package com.hcl.medicalclaim.dto;

public class ClaimAppoveDto {
	
	private long policyId;
	private long requestId;
	private long approvalId;
	private String comments;
	
	public long getPolicyId() {
		return policyId;
	}
	public void setPolicyId(long policyId) {
		this.policyId = policyId;
	}
	public long getRequestId() {
		return requestId;
	}
	public void setRequestId(long requestId) {
		this.requestId = requestId;
	}
	
	public long getApprovalId() {
		return approvalId;
	}
	public void setApprovalId(long approvalId) {
		this.approvalId = approvalId;
	}
	
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	

}
