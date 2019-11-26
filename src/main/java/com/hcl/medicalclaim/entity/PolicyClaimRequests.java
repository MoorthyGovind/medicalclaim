package com.hcl.medicalclaim.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class PolicyClaimRequests extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private long id;

	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="policy_id")
	private Policy policyId;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="hospital_id")
	private Hospital hospitalId;
	
	@Column(name="claim_amount")
	private double claimAmount;

	@Column(name="claim_date")
	private Date claimDate;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setPolicyId(Policy policyId) {
		this.policyId = policyId;
	}

	public void setHospitalId(Hospital hospitalId) {
		this.hospitalId = hospitalId;
	}

	public void setClaimAmount(double claimAmount) {
		this.claimAmount = claimAmount;
	}

	public void setClaimDate(Date claimDate) {
		this.claimDate = claimDate;
	}
}
