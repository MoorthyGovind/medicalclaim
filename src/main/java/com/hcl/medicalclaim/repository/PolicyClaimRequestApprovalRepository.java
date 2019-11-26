package com.hcl.medicalclaim.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hcl.medicalclaim.entity.PolicyClaimRequestApproval;
@Repository
@Transactional
public interface PolicyClaimRequestApprovalRepository extends CrudRepository<PolicyClaimRequestApproval, Long> {

	
	List<PolicyClaimRequestApproval> findByApprovalIdAndIsApprovedFalseAndIsRejectedFalse(long requestId);
	

	PolicyClaimRequestApproval findByRequestIdAndApprovalId(long requestId, long approvalId);
}
