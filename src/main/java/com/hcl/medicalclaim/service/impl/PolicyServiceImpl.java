package com.hcl.medicalclaim.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.medicalclaim.common.AppConstants;
import com.hcl.medicalclaim.common.TrackPolicyStatus;
import com.hcl.medicalclaim.dto.ClaimAppoveDto;
import com.hcl.medicalclaim.dto.PolicyClaimDto;
import com.hcl.medicalclaim.dto.PolicyDto;
import com.hcl.medicalclaim.dto.UserIdDto;
import com.hcl.medicalclaim.dto.ViewClaimDto;
import com.hcl.medicalclaim.entity.Hospital;
import com.hcl.medicalclaim.entity.Policy;
import com.hcl.medicalclaim.entity.PolicyClaimRequestApproval;
import com.hcl.medicalclaim.entity.PolicyClaimRequests;
import com.hcl.medicalclaim.entity.PolicyStatus;
import com.hcl.medicalclaim.entity.User;
import com.hcl.medicalclaim.repository.HospitalRepository;
import com.hcl.medicalclaim.repository.PolicyClaimRequestApprovalRepository;
import com.hcl.medicalclaim.repository.PolicyClaimRequestsRepository;
import com.hcl.medicalclaim.repository.PolicyRepository;
import com.hcl.medicalclaim.repository.PolicyStatusRepository;
import com.hcl.medicalclaim.repository.UserRepository;
import com.hcl.medicalclaim.response.ApiResponse;
import com.hcl.medicalclaim.service.PolicyService;
import com.hcl.medicalclaim.util.CommonUtils;
import com.hcl.medicalclaim.util.ConverterUtil;

@Service
public class PolicyServiceImpl implements PolicyService{
	private static final Logger logger = LoggerFactory.getLogger(PolicyServiceImpl.class);
	
	@Autowired
	PolicyRepository policyRepository;
	
	@Autowired
	PolicyClaimRequestsRepository policyClaimRequestsRepository;
	
	@Autowired
	PolicyClaimRequestApprovalRepository policyApprovalRepository;
	
	@Autowired
	PolicyStatusRepository policyStatusRepository;
	
	@Autowired
	HospitalRepository hospitalRepository;

	@Autowired
	UserRepository userRepository;
	
	@Override
	public Policy getPolicyById(PolicyDto dto){
		return policyRepository.findByPolicyNo(dto.getPolicyNo());
	}
	
	/**claimListByUserId
	 * UserIdDto userIdDto
	 */
	@Override
	public List<ViewClaimDto> claimListByUserId(UserIdDto userDto){
		List<ViewClaimDto> dtos = new ArrayList<>();
		User user = userRepository.findByUserId(userDto.getUserId());
		Optional<User> isUser = Optional.ofNullable(user);
		if(isUser.isPresent()) {
			//Claims list based on user id with isApproval & isRejected false.
			List<PolicyClaimRequestApproval> claims = policyApprovalRepository.findByApprovalIdAndIsApprovedFalseAndIsRejectedFalse(user.getId());
			
			//ForEach the claims list by each claim
			claims.forEach(claim ->{
				Policy policy = policyRepository.findById(claim.getPolicyId().getId());
				dtos.add(ConverterUtil.convertDto(policy, claim));
			});
		}
		
		return dtos;
		
	}
	
	/**
	 * @PolicyClaimDto claimDto
	 */
	@Override
	public ApiResponse createPolicyClaim(PolicyClaimDto claimDto) {
		PolicyClaimRequests entity = new PolicyClaimRequests();
		ApiResponse response = new ApiResponse(null, 0, null);
		
		try {
			//Find the Policy & Hospital details by id's
			Policy policy = policyRepository.findByPolicyNo(claimDto.getPolicyNo());
			Hospital hospital = hospitalRepository.findById(claimDto.getHospitalId());
			
			//Check nullable conditions for policy and hospitals
			Optional<Policy> isPolicyExists = Optional.ofNullable(policy);
			Optional<Hospital> isHospitalExists = Optional.ofNullable(hospital);
			if(isPolicyExists.isPresent() && isHospitalExists.isPresent()) {
				//Setting the Entity Values
				entity.setPolicyId(policy);
				entity.setHospitalId(hospital);
				entity.setClaimDate(new Date());
				entity.setStatus(true);
				
				//Calculate the claim Amount.
				double claimAmount =  new CommonUtils().getClaimAmount(policy.getPolicyAmount(), hospital.getClaimPercentage());
				entity.setClaimAmount(claimAmount);
				
				PolicyClaimRequests claimRequest = policyClaimRequestsRepository.save(entity);
				
				//Track Policy Status for Claim Generated
				savePolicyStatus(policy, TrackPolicyStatus.TrackPolicy.CG.getStatus());

				//Track Policy Status for Waiting for Approval
				savePolicyStatus(policy, TrackPolicyStatus.TrackPolicy.WA.getStatus());
				
				// Check the claim amount is greater than cap amount
				 if(policy.getCapAmount() > claimAmount){ 
					 // Send 1 level approval 
					 createClaimRequestsByApprovalLevel(claimRequest, policy, AppConstants.APPROVAL_LEVEL_1);

				  }else {
					  //Send 2 level approval
					  createClaimRequestsByApprovalLevel(claimRequest, policy, AppConstants.APPROVAL_LEVEL_2);
				  }

				  response.setStatus(AppConstants.SUCCESS);
					response.setMessage(AppConstants.SUCCESS);
				  				
			}else {
				response.setStatus(AppConstants.SUCCESS);
				response.setMessage(AppConstants.NO_RECORD_FOUND);
			}
		}
		catch(Exception ex) {
			logger.error("Exception while createPolicyClaim: ", ex);
		}
		
		return response;
	}
	
	/**claimApprove
	 * @ClaimAppoveDto dto
	 */
	@Override
	public ApiResponse claimApprove(ClaimAppoveDto dto) 
	{ 
		ApiResponse response = new ApiResponse(null, 0, null);
		try {
			// Find Claim Request Approval
			PolicyClaimRequestApproval approval = policyApprovalRepository.findByRequestIdAndApprovalId(dto.getRequestId(), dto.getApprovalId());
			Optional<PolicyClaimRequestApproval> isApproval = Optional.ofNullable(approval);
			if(isApproval.isPresent()) {
				approval.setApproved(true);
				approval.setComments(dto.getComments());
				policyApprovalRepository.save(approval);
				
				//Check Approval Level
				User user = userRepository.findById(dto.getApprovalId());
				Optional<User> isUserPresent = Optional.ofNullable(user);
				if(isUserPresent.isPresent()) {
					//Track Policy Status for Approval Status
					saveClaimPolicyStatus(approval, user);
				}
				
				response.setMessage(AppConstants.SUCCESS_MESSAGE);
			}else {
				response.setMessage(AppConstants.NO_RECORD_FOUND);
			}

			response.setStatus(AppConstants.SUCCESS);
		}catch(Exception ex) {
			logger.error("Exception while claimApprove: ", ex);
		}
		
		return response;
	}
	
	/**claimReject
	 * @ClaimAppoveDto dto
	 */
	@Override
	public ApiResponse claimReject(ClaimAppoveDto dto) 
	{ 
		ApiResponse response = new ApiResponse(null, 0, null);
		try {
			// Find Claim Request Approval
			PolicyClaimRequestApproval approval = policyApprovalRepository.findByRequestIdAndApprovalId(dto.getRequestId(), dto.getApprovalId());
			Optional<PolicyClaimRequestApproval> isApproval = Optional.ofNullable(approval);
			if(isApproval.isPresent()) {
				approval.setRejected(true);
				approval.setComments(dto.getComments());
				policyApprovalRepository.save(approval);
				
				//Track Policy Status for Rejected Case
				savePolicyStatus(approval.getPolicyId(), TrackPolicyStatus.TrackPolicy.PR.getStatus());
				
				response.setMessage(AppConstants.SUCCESS_MESSAGE);
			}else {
				response.setMessage(AppConstants.NO_RECORD_FOUND);
			}

			response.setStatus(AppConstants.SUCCESS);
		}catch(Exception ex) {
			logger.error("Exception while claimReject: ", ex);
		}
		
		return response;
	}
	
	/**
	 * @PolicyDto dto
	 */
	@Override
	public List<PolicyStatus> getPolicyStatus(PolicyDto dto){
		/* Add or Remore the policy status in the arraylist*/
		List<PolicyStatus> policyStatusList = new ArrayList<>();
		
		//Find the policy detail by policy no.
		Policy policy = policyRepository.findByPolicyNo(dto.getPolicyNo());
		
		//Check the nullable or not the policy detail.
		Optional<Policy> isPolicy = Optional.ofNullable(policy);
		if(isPolicy.isPresent()) {
			policyStatusList = policyStatusRepository.findByPolicyId(policy);
		}
		return policyStatusList;
	}
	
	/**
	 * 
	 * @param policy
	 * @return
	 */
	public void createClaimRequestsByApprovalLevel(PolicyClaimRequests claimRequests, Policy policy,
			int levelId) {
	    try 
	    {
	    	switch(levelId) {
		    	case 1:
		    		//Find 1st level approvals
			  	  	List<User> firstLevelUsers = userRepository.approvalLevelUsers(levelId);
			  	    saveClaimApproval(firstLevelUsers, policy, claimRequests);
		    		break;
		    	case 2:
		    		//Find 2st level approvals
			  	  	List<User> secondLevelUsers = userRepository.findAll();
			  	    saveClaimApproval(secondLevelUsers, policy, claimRequests);
		    		break;
		    	default:
	    		  break;
	    		}

		}
	    catch(Exception ex) {
			logger.error("Exception while createClaimRequestsByApprovalLevel: ", ex);
	    }
	}
	
	/**
	 * saveClaimApproval
	 * @param users
	 * @param policy
	 * @param claimRequests
	 */
	public void saveClaimApproval(List<User> users, Policy policy, PolicyClaimRequests claimRequests) {
		//Iterate the list of approval users
		users.forEach(user ->{
			 PolicyClaimRequestApproval approval = new PolicyClaimRequestApproval();
			  approval.setApprovalId(user.getId());
			  approval.setPolicyId(policy);
			  if(claimRequests != null) {
				  approval.setRequestId(claimRequests.getId());
			  }
			  approval.setStatus(true);
				
			  //save the approval details
			  policyApprovalRepository.save(approval);
		});
	}
	
	/**
	 * 
	 * @param approval
	 * @param user
	 */
	public void saveClaimPolicyStatus(PolicyClaimRequestApproval approval, User user) {
		//Policy Approval id 
		int policyApprovalId =(int)user.getApprovalLevel();
		switch(policyApprovalId) {
			case 1:
				savePolicyStatus(approval.getPolicyId(), TrackPolicyStatus.TrackPolicy.FA.getStatus());
				break;
			case 2:
				savePolicyStatus(approval.getPolicyId(), TrackPolicyStatus.TrackPolicy.SA.getStatus());
                break;
			default:
	    		  break;
		}
	}
	
	/**
	 * savePolicyStatus
	 */
	public void savePolicyStatus(Policy policy, String policyStatusValue) {
		//Track Policy Status
		PolicyStatus policyStatus = new PolicyStatus();
		policyStatus.setPolicyId(policy);
		policyStatus.setPolicyStatusValue(policyStatusValue);
		
		policyStatus.setStatus(true);
		policyStatusRepository.save(policyStatus);
	}
}
