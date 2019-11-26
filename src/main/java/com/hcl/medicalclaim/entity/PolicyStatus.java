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
public class PolicyStatus extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private long id;

	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="policy_id")
	private Policy policyId;
	
	@Column(name="policy_status")
	private String policyStatusValue;
	
	public Policy getPolicyId() {
		return policyId;
	}

	public String getPolicyStatusValue() {
		return policyStatusValue;
	}

	public void setPolicyId(Policy policyId) {
		this.policyId = policyId;
	}

	public void setPolicyStatusValue(String policyStatusValue) {
		this.policyStatusValue = policyStatusValue;
	}
}
