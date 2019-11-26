package com.hcl.medicalclaim.dto;

import java.util.Date;

public class ViewClaimDto {
	
	private String policyNo;
	private Date policyDate;
	private double policyAmount;
	private String claimantName;
	private long requestId;
	private long approvedId;
	
	public String getPolicyNo() {
		return policyNo;
	}
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}
	
	public long getRequestId() {
		return requestId;
	}
	public void setRequestId(long requestId) {
		this.requestId = requestId;
	}
	
	public Date getPolicyDate() {
		return policyDate;
	}
	public void setPolicyDate(Date policyDate) {
		this.policyDate = policyDate;
	}
	public double getPolicyAmount() {
		return policyAmount;
	}
	public void setPolicyAmount(double policyAmount) {
		this.policyAmount = policyAmount;
	}
	public String getClaimantName() {
		return claimantName;
	}
	public void setClaimantName(String claimantName) {
		this.claimantName = claimantName;
	}
	
	public long getApprovedId() {
		return approvedId;
	}
	public void setApprovedId(long approvedId) {
		this.approvedId = approvedId;
	}
}
