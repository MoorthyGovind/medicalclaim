package com.hcl.medicalclaim.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.medicalclaim.common.AppConstants;
import com.hcl.medicalclaim.dto.ClaimAppoveDto;
import com.hcl.medicalclaim.dto.PolicyClaimDto;
import com.hcl.medicalclaim.dto.PolicyDto;
import com.hcl.medicalclaim.dto.UserIdDto;
import com.hcl.medicalclaim.dto.ViewClaimDto;
import com.hcl.medicalclaim.entity.Policy;
import com.hcl.medicalclaim.entity.PolicyStatus;
import com.hcl.medicalclaim.response.ApiResponse;
import com.hcl.medicalclaim.response.EntityResponse;
import com.hcl.medicalclaim.service.PolicyService;

@RestController
public class PolicyController {
	
	@Autowired
	PolicyService policyService;
	
	/**
	 * 
	 * @param policyDto
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping(value="/policy", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EntityResponse> getPolicyById(@RequestBody PolicyDto policyDto) {
		EntityResponse<Policy> response;
		Policy policy = policyService.getPolicyById(policyDto);
		Optional<Policy> isPolicy = Optional.ofNullable(policy);
		if(isPolicy.isPresent()) {
			response = new EntityResponse(AppConstants.SUCCESS, AppConstants.SUCCESS, policy);
			
		}else {
			response = new EntityResponse(AppConstants.FAILURE, AppConstants.NO_RECORD_FOUND, null);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	
	/**
	 * 
	 * @param userDto
	 * @return
	 */
	@PostMapping(value="/claims", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ViewClaimDto>> claimListByUser(@RequestBody UserIdDto userIdDto){
		List<ViewClaimDto> dtos = policyService.claimListByUserId(userIdDto);
		return new ResponseEntity<>(dtos, HttpStatus.OK);
	}
		
	/**
	 * 
	 * @param policyClaimDto
	 * @return
	 */
	@PostMapping(value="/claim/policy", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponse> createPolicyClaim(@RequestBody PolicyClaimDto policyClaimDto) {
		 ApiResponse response = policyService.createPolicyClaim(policyClaimDto);
		 Optional<ApiResponse> isResponse = Optional.ofNullable(response);
		 if(isResponse.isPresent()) {
			 response.setStatusCode(HttpStatus.OK.value());
		 }
		 return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param approveDto
	 * @return
	 */
	@PostMapping(value="/claim/approve", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponse> claimApprovedByUser(@RequestBody ClaimAppoveDto approveDto){
		ApiResponse response = policyService.claimApprove(approveDto);
		Optional<ApiResponse> isResponse = Optional.ofNullable(response);
		if(isResponse.isPresent()) {
			 response.setStatusCode(HttpStatus.OK.value());
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param approveDto
	 * @return
	 */
	@PostMapping(value="/claim/reject", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponse> claimRejectedByUser(@RequestBody ClaimAppoveDto approveDto){
		ApiResponse response = policyService.claimReject(approveDto);
		Optional<ApiResponse> isResponse = Optional.ofNullable(response);
		if(isResponse.isPresent()) {
			 response.setStatusCode(HttpStatus.OK.value());
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	
	/**
	 * 
	 * @param policyDto
	 * @return
	 */
	@PostMapping(value="/check/policy-status", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<PolicyStatus>> getPolicyStatus(@RequestBody PolicyDto policyDto) {
		List<PolicyStatus> policyStatusList = policyService.getPolicyStatus(policyDto);
		return new ResponseEntity<>(policyStatusList, HttpStatus.OK);
	}

}
