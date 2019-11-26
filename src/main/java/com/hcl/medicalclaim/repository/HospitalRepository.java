package com.hcl.medicalclaim.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hcl.medicalclaim.entity.Hospital;

@Repository
@Transactional
public interface HospitalRepository extends CrudRepository<Hospital, Long> {
	
	@Override
    List<Hospital> findAll();

    Hospital findById(long id);

}
