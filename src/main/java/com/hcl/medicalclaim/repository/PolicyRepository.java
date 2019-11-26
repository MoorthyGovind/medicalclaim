package com.hcl.medicalclaim.repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hcl.medicalclaim.entity.Policy;

@Repository
@Transactional
public interface PolicyRepository extends CrudRepository<Policy, Long> {

	Policy findByPolicyNo(String policyNo);

	Policy findById(long policyId);
}
