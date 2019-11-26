package com.hcl.medicalclaim.dto;

import javax.validation.constraints.NotBlank;

public class PolicyDto {
	
	@NotBlank
	private String policyNo;
	
	public String getPolicyNo() {
		return policyNo;
	}
	
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

}
