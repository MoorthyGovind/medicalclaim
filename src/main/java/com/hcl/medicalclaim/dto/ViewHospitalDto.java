package com.hcl.medicalclaim.dto;

public class ViewHospitalDto {
	private String hospitalCode;
	private String hospitalName;
	private String address1;
	private String address2;
	private String place;
	private Float claimPercentage;
	
	public String getHospitalCode() {
		return hospitalCode;
	}
	public void setHospitalCode(String hospitalCode) {
		this.hospitalCode = hospitalCode;
	}
	public String getHospitalName() {
		return hospitalName;
	}
	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public Float getClaimPercentage() {
		return claimPercentage;
	}
	public void setClaimPercentage(Float claimPercentage) {
		this.claimPercentage = claimPercentage;
	}
	
}
