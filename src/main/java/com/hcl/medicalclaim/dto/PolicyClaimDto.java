package com.hcl.medicalclaim.dto;

import javax.validation.constraints.NotBlank;

public class PolicyClaimDto {
	
	@NotBlank
	private String policyNo;
	private long hospitalId;
	private String reason;
	
	public String getPolicyNo() {
		return policyNo;
	}
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}
	
	public long getHospitalId() {
		return hospitalId;
	}
	public void setHospitalId(long hospitalId) {
		this.hospitalId = hospitalId;
	}
	
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	

}
