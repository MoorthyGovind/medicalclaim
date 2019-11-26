package com.hcl.medicalclaim.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class PolicyClaimRequestApproval extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private String id;

	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="policy_id")
	private Policy policyId;
	
	@Column(name="request_id")
	private long requestId;
	
	@JoinColumn(name="approval_id")
	private long approvalId;
	
	@Column(name="is_approved")
	private boolean isApproved;

	@Column(name="is_rejected")
	private boolean isRejected;
	
	@Column(name="comments")
	private String comments;
	
	public void setId(String id) {
		this.id = id;
	}

	public Policy getPolicyId() {
		return policyId;
	}

	public void setPolicyId(Policy policyId) {
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

	public boolean isApproved() {
		return isApproved;
	}

	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}

	public boolean isRejected() {
		return isRejected;
	}

	public void setRejected(boolean isRejected) {
		this.isRejected = isRejected;
	}
	
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
}
