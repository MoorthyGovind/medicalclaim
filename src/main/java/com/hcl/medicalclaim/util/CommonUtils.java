package com.hcl.medicalclaim.util;

public class CommonUtils {
	
	/**
	 * 
	 * @param value
	 * @return
	 */
	public double getClaimAmount(double policyAmt, double claimPercent) {
		return policyAmt * (claimPercent / 100);
	}

}
