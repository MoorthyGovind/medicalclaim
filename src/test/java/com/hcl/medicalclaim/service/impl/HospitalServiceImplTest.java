package com.hcl.medicalclaim.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hcl.medicalclaim.dto.ViewHospitalDto;
import com.hcl.medicalclaim.entity.Hospital;
import com.hcl.medicalclaim.repository.HospitalRepository;

@RunWith(SpringJUnit4ClassRunner.class)
public class HospitalServiceImplTest {

	@InjectMocks
	HospitalServiceImpl hospitalServiceImpl;
	
	@Mock
	HospitalRepository hospitalRepository;
	
	@Before
	public void init() {
	        MockitoAnnotations.initMocks(this);
	}
	 
	@Test
	public void testGetAll() {
		// given
		List<Hospital> expectedHospitals = new ArrayList<>();; 
        Hospital hospital = new Hospital();
        hospital.setId(1L);
        hospital.setHospitalCode("SG-BLR");
        hospital.setHospitalName("SG Hospital");
        hospital.setAddress1("Phase 1, Electronic City");
        hospital.setAddress2("Electronic City");
        hospital.setPlace("Bangalore");
        hospital.setClaimPercentage(80);
        expectedHospitals.add(hospital);
		
		//return mocked result set on find
		when(hospitalRepository.findAll()).thenReturn(expectedHospitals);
        List<ViewHospitalDto> findAll = hospitalServiceImpl.getAll();
        assertThat(findAll).hasSize(1);


	}
}
