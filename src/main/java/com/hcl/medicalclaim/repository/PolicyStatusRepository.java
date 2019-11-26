package com.hcl.medicalclaim.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hcl.medicalclaim.entity.Policy;
import com.hcl.medicalclaim.entity.PolicyStatus;

@Repository
@Transactional
public interface PolicyStatusRepository extends CrudRepository<PolicyStatus, Long> {

	List<PolicyStatus> findByPolicyId(Policy policy);
}
