package com.hcl.medicalclaim.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Hospital extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private long id;

	@Column(name="hospital_code")
	private String hospitalCode;
	
	@Column(name="hospital_name")
	private String hospitalName;
	
	@Column(name="address_1")
	private String address1;

	@Column(name="address_2")
	private String address2;

	@Column(name="place")
	private String place;

	@Column(name="claim_percentage")
	private float claimPercentage;

	public long getId() {
		return id;
	}

	public String getHospitalCode() {
		return hospitalCode;
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public String getAddress1() {
		return address1;
	}

	public String getAddress2() {
		return address2;
	}

	public String getPlace() {
		return place;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setHospitalCode(String hospitalCode) {
		this.hospitalCode = hospitalCode;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	
	public void setPlace(String place) {
		this.place = place;
	}

	public float getClaimPercentage() {
		return claimPercentage;
	}

	public void setClaimPercentage(float claimPercentage) {
		this.claimPercentage = claimPercentage;
	}
}
