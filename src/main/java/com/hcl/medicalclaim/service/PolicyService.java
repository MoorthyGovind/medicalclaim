package com.hcl.medicalclaim.service;

import java.util.List;

import com.hcl.medicalclaim.dto.ClaimAppoveDto;
import com.hcl.medicalclaim.dto.PolicyClaimDto;
import com.hcl.medicalclaim.dto.PolicyDto;
import com.hcl.medicalclaim.dto.UserIdDto;
import com.hcl.medicalclaim.dto.ViewClaimDto;
import com.hcl.medicalclaim.entity.Policy;
import com.hcl.medicalclaim.entity.PolicyStatus;
import com.hcl.medicalclaim.response.ApiResponse;

public interface PolicyService {

	public Policy getPolicyById(PolicyDto policyDto);
	
	public List<ViewClaimDto> claimListByUserId(UserIdDto claimDto);
	
	public ApiResponse createPolicyClaim(PolicyClaimDto claimDto);

	public ApiResponse claimApprove(ClaimAppoveDto claimDto);
	
	public ApiResponse claimReject(ClaimAppoveDto claimDto);

	public List<PolicyStatus> getPolicyStatus(PolicyDto policyDto);
}
