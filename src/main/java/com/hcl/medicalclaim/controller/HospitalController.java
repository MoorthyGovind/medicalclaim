package com.hcl.medicalclaim.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.medicalclaim.dto.ViewHospitalDto;
import com.hcl.medicalclaim.service.HospitalService;

@RestController
public class HospitalController {

	private static final Logger logger = LoggerFactory.getLogger(HospitalController.class);
	@Autowired
	HospitalService hospitalService;
	
	/**
	 * getAll hospital lists
	 * @return
	 */
	@GetMapping(value="/hospitals")
	public ResponseEntity<List<ViewHospitalDto>> getAll(){
		logger.info("getHospitalList...");
		List<ViewHospitalDto> hospitals = hospitalService.getAll();
		return new ResponseEntity<>(hospitals, HttpStatus.OK);
	}
}
