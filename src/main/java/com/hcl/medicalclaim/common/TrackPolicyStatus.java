package com.hcl.medicalclaim.common;

public class TrackPolicyStatus {

	public enum TrackPolicy{
	CG("Claim Generated"),
	WA("Waiting for Approval"),
	FA("1st Level Approved"),
	SA("2st Level Approved"),
	PR("Policy Rejected"),
	PC("Policy Claimed");
	
	private String status;
	
	TrackPolicy(String policyStatus){
		this.status = policyStatus;
	}
	
	public String getStatus() {
		return status;
	}
	
	}
}
