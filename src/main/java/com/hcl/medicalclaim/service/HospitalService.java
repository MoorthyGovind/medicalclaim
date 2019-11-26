package com.hcl.medicalclaim.service;

import java.util.List;

import com.hcl.medicalclaim.dto.ViewHospitalDto;

@FunctionalInterface
public interface HospitalService {

	public List<ViewHospitalDto> getAll();
}
