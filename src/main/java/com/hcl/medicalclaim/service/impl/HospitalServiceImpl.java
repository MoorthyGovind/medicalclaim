package com.hcl.medicalclaim.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.medicalclaim.dto.ViewHospitalDto;
import com.hcl.medicalclaim.entity.Hospital;
import com.hcl.medicalclaim.repository.HospitalRepository;
import com.hcl.medicalclaim.service.HospitalService;
import com.hcl.medicalclaim.util.ConverterUtil;

@Service
public class HospitalServiceImpl implements HospitalService{

	@Autowired
	HospitalRepository hospitalRepository;
	
	/**
	 * get all the hospitals in the list.
	 */
	@Override
	public List<ViewHospitalDto> getAll(){
		List<Hospital> hospitals = hospitalRepository.findAll();
        List<ViewHospitalDto> hospitalDtos = new ArrayList<>();
		hospitals.forEach(hospital -> {
			hospitalDtos.add(ConverterUtil.convertHospitalDto(hospital));
		});
		return hospitalDtos;
	}
	
}
