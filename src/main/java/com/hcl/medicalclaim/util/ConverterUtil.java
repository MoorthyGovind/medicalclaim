package com.hcl.medicalclaim.util;

import com.hcl.medicalclaim.dto.ViewClaimDto;
import com.hcl.medicalclaim.dto.ViewHospitalDto;
import com.hcl.medicalclaim.entity.Hospital;
import com.hcl.medicalclaim.entity.Policy;
import com.hcl.medicalclaim.entity.PolicyClaimRequestApproval;

public class ConverterUtil {

	private ConverterUtil(){
	}
	
	public static ViewClaimDto convertDto(Policy policy, PolicyClaimRequestApproval claim) {
		ViewClaimDto dto = new ViewClaimDto();
		dto.setPolicyNo(policy.getPolicyNo());
		dto.setPolicyDate(policy.getPolicyDate());
		dto.setPolicyAmount(policy.getPolicyAmount());
		dto.setClaimantName(policy.getClaimantName());
		dto.setRequestId(claim.getRequestId());
		dto.setApprovedId(claim.getApprovalId());
		return dto;
	}
	
	public static ViewHospitalDto convertHospitalDto(Hospital hospital) {
		ViewHospitalDto dto = new ViewHospitalDto();
		dto.setHospitalCode(hospital.getHospitalCode());
		dto.setHospitalName(hospital.getHospitalName());
		dto.setAddress1(hospital.getAddress1());
		dto.setAddress2(hospital.getAddress2());
		dto.setPlace(hospital.getPlace());
		dto.setClaimPercentage(hospital.getClaimPercentage());
		return dto;
	}
	
}
