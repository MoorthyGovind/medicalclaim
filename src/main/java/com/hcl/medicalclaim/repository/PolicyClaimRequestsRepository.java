package com.hcl.medicalclaim.repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hcl.medicalclaim.entity.PolicyClaimRequests;

@Repository
@Transactional
public interface PolicyClaimRequestsRepository extends CrudRepository<PolicyClaimRequests, Long> {

	
}
