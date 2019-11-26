package com.hcl.medicalclaim.service.impl;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
import com.hcl.medicalclaim.util.ConverterUtil;

@RunWith(SpringJUnit4ClassRunner.class)
public class PolicyServiceImplTest {
	@InjectMocks
	PolicyServiceImpl policyServiceImpl;
	
	@Mock
	PolicyRepository policyRepository;
	@Mock
	PolicyStatusRepository policyStatusRepository;
	
	@Mock
	UserRepository userRepository;
	
	@Mock
	HospitalRepository hospitalRepository;
	
	@Mock
	PolicyClaimRequestApprovalRepository policyApprovalRepository;
	
	@Mock
	PolicyClaimRequestsRepository policyClaimRequestsRepository;
	
	@Mock
	ConverterUtil converterUtil;
	
	Policy policy = new Policy();	
	User user = new User();	
	PolicyDto policyDto = new PolicyDto();
	ViewClaimDto dto = new ViewClaimDto();

	@Before
	public void init() {
	        MockitoAnnotations.initMocks(this);
			policy.setId(1L);
			policy.setPolicyNo("P00001");
			policy.setPolicyName("Medical Claim");
			policy.setPolicyDate(new Date());
			policy.setClaimantName("Moorthy");
			policy.setPolicyAmount(45000.00);
			policy.setCapAmount(40000.00);

			user.setId(1L);
			user.setUserId("moorthy127@gmail.com");
			user.setPassword("Start@123");
			user.setApprovalLevel(1L);
			user.setStatus(true);
			
			dto.setPolicyNo(policy.getPolicyNo());
			dto.setPolicyDate(policy.getPolicyDate());
			dto.setPolicyAmount(policy.getPolicyAmount());
			dto.setClaimantName(policy.getClaimantName());
	}
	
	@Test
	public void testGetPolicyById() {
		when(policyRepository.findByPolicyNo("P00001")).thenReturn(policy);
		policyDto.setPolicyNo("P00001");
		Policy policyDetail = policyServiceImpl.getPolicyById(policyDto);
        assertEquals(policy.getPolicyNo(), policyDetail.getPolicyNo());
	}
	
	@Test
	public void testClaimListByUserId() throws Exception{
		PolicyClaimRequestApproval approval = new PolicyClaimRequestApproval();
		approval.setId("1");
		approval.setPolicyId(policy);
		
		List<PolicyClaimRequestApproval> claims = new ArrayList<>();
		claims.add(approval);
		UserIdDto userIdDto = new UserIdDto();
		userIdDto.setUserId("moorthy127@gmail.com");
		
		when(userRepository.findByUserId("moorthy127@gmail.com")).thenReturn(user);
    	when(policyApprovalRepository.findByApprovalIdAndIsApprovedFalseAndIsRejectedFalse(user.getId())).thenReturn(claims);
        when(policyRepository.findById(policy.getId())).thenReturn(policy);
        
        List<ViewClaimDto> viewClaimDtos = policyServiceImpl.claimListByUserId(userIdDto);
        assertThat(viewClaimDtos).hasSize(1);
	}
	
	@Test
	public void testCreatePolicyClaim() {
		PolicyClaimDto policyClaimDto = new PolicyClaimDto();
    	policyClaimDto.setHospitalId(1L);
    	policyClaimDto.setPolicyNo("P00001");
    	policyClaimDto.setReason("Policy Created");
    	
    	Hospital hospital = new Hospital();
    	hospital.setId(1L);
    	PolicyClaimRequests status = new PolicyClaimRequests();
    	status.setId(1L);
    	
    	PolicyClaimRequestApproval approval = new PolicyClaimRequestApproval();
    	List<User> users = new ArrayList<>();
    	users.add(user);
    	
		when(policyRepository.findByPolicyNo(policy.getPolicyNo())).thenReturn(policy);
		when(hospitalRepository.findById(1L)).thenReturn(hospital);
		when(policyClaimRequestsRepository.save(status)).thenReturn(status);
		when(userRepository.approvalLevelUsers(1L)).thenReturn(users);
		when(policyApprovalRepository.save(approval)).thenReturn(approval);
		
		ApiResponse response = policyServiceImpl.createPolicyClaim(policyClaimDto);
		assertEquals(response.getStatus(), AppConstants.SUCCESS);

	}
	
	/*i.e claim amount is greater than cap amount*/
	@Test
	public void testCreatePolicyClaimSend2LevelApprovals() {
		PolicyClaimDto policyClaimDto = new PolicyClaimDto();
    	policyClaimDto.setHospitalId(1L);
    	policyClaimDto.setPolicyNo("P00002");
    	policyClaimDto.setReason("Policy Created");

    	policy.setPolicyAmount(60000.00);
    	policy.setCapAmount(45000.00);
    	Hospital hospital = new Hospital();
    	hospital.setId(1L);
    	hospital.setClaimPercentage(80);
    	
    	PolicyClaimRequests status = new PolicyClaimRequests();
    	status.setId(1L);
    	PolicyClaimRequestApproval approval = new PolicyClaimRequestApproval();
    	List<User> users = new ArrayList<>();
    	users.add(user);
    	
    	when(policyRepository.findByPolicyNo("P00002")).thenReturn(policy);
		when(hospitalRepository.findById(1L)).thenReturn(hospital);
		when(policyClaimRequestsRepository.save(status)).thenReturn(status);
		when(userRepository.approvalLevelUsers(1L)).thenReturn(users);
		when(policyApprovalRepository.save(approval)).thenReturn(approval);
		ApiResponse response = policyServiceImpl.createPolicyClaim(policyClaimDto);
		assertEquals(response.getStatus(), AppConstants.SUCCESS);
	}
	
	@Test
	public void testCreatePolicyClaimByNoRecords() {
		PolicyClaimDto policyClaimDto = new PolicyClaimDto();
    	policyClaimDto.setHospitalId(1L);
    	policyClaimDto.setPolicyNo("P00001");
    	policyClaimDto.setReason("Policy Created");
    	
		when(policyRepository.findByPolicyNo(policy.getPolicyNo())).thenReturn(policy);
		when(hospitalRepository.findById(1L)).thenReturn(null);
		ApiResponse response = policyServiceImpl.createPolicyClaim(policyClaimDto);
		assertEquals(response.getStatus(), AppConstants.SUCCESS);
	}
	
	
    @Test
    public void testClaimApprove() {
    	ClaimAppoveDto approveDto = new ClaimAppoveDto();
    	approveDto.setApprovalId(1L);
    	approveDto.setPolicyId(1);
    	approveDto.setRequestId(1);
    	approveDto.setComments("User Approved");
    	
		when(policyApprovalRepository.findByRequestIdAndApprovalId(1L, 1L)).thenReturn(new PolicyClaimRequestApproval());
		when(userRepository.findById(1L)).thenReturn(user);
		ApiResponse response = policyServiceImpl.claimApprove(approveDto);
		assertEquals(response.getStatus(), AppConstants.SUCCESS);
    }
    
    @Test
    public void testClaimApproveNoRecordsFound() {
    	ClaimAppoveDto approveDto = new ClaimAppoveDto();
    	approveDto.setApprovalId(0L);
    	approveDto.setPolicyId(0);
    	approveDto.setRequestId(0);
    	approveDto.setComments("User Approved");
    	
		when(policyApprovalRepository.findByRequestIdAndApprovalId(0L, 0L)).thenReturn(null);
		ApiResponse response = policyServiceImpl.claimApprove(approveDto);
		assertEquals(response.getStatus(), AppConstants.SUCCESS);
    }
    
    @Test
    public void testClaimReject() {
    	ClaimAppoveDto approveDto = new ClaimAppoveDto();
    	approveDto.setApprovalId(1L);
    	approveDto.setPolicyId(1);
    	approveDto.setRequestId(1);
    	approveDto.setComments("User Rejected");
    	
		when(policyApprovalRepository.findByRequestIdAndApprovalId(1L, 1L)).thenReturn(new PolicyClaimRequestApproval());
		when(userRepository.findById(1L)).thenReturn(new User());
		ApiResponse response = policyServiceImpl.claimReject(approveDto);
		assertEquals(response.getStatus(), AppConstants.SUCCESS);
    }
    
    @Test
    public void testClaimRejectNoRecordsFound() {
    	ClaimAppoveDto approveDto = new ClaimAppoveDto();
    	approveDto.setApprovalId(0L);
    	approveDto.setPolicyId(0);
    	approveDto.setRequestId(0);
    	approveDto.setComments("User Rejected");
    	
		when(policyApprovalRepository.findByRequestIdAndApprovalId(0L, 0L)).thenReturn(null);
		ApiResponse response = policyServiceImpl.claimReject(approveDto);
		assertEquals(response.getStatus(), AppConstants.SUCCESS);
    }
	
	@Test
	public void testGetPolicyStatus() {
		PolicyDto dto = new PolicyDto();
		dto.setPolicyNo("P00001");
		List<PolicyStatus> policyStatusList = new ArrayList<>();
		when(policyRepository.findByPolicyNo(dto.getPolicyNo())).thenReturn(policy);
		when(policyStatusRepository.findByPolicyId(policy)).thenReturn(policyStatusList);

		policyStatusList = policyServiceImpl.getPolicyStatus(dto);
        assertThat(policyStatusList).hasSize(0);
	}
	
	@Test
	public void testSavePolicyStatus() {
		PolicyStatus status = new PolicyStatus();
		when(policyStatusRepository.save(status)).thenReturn(status);

		policyServiceImpl.savePolicyStatus(policy, TrackPolicyStatus.TrackPolicy.CG.getStatus());
	}
	
	@Test
	public void testCreateClaimRequestsByApprovalLevel() {
		List<User> users = new ArrayList<>();
    	users.add(user);
    	PolicyClaimRequests claimRequests = new PolicyClaimRequests();
    	claimRequests.setId(1L);
		when(userRepository.approvalLevelUsers(1L)).thenReturn(users);
		policyServiceImpl.createClaimRequestsByApprovalLevel(claimRequests, policy, 1);
	}
	
}
