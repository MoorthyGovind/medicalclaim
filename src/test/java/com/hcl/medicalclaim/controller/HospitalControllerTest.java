package com.hcl.medicalclaim.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.hcl.medicalclaim.controller.HospitalController;
import com.hcl.medicalclaim.service.impl.HospitalServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
public class HospitalControllerTest {

	@InjectMocks
	HospitalController hospitalController;
	
	@Mock
	HospitalServiceImpl hospitalService;
	private MockMvc mockMvc;
	
	@Before
	public void init() {
		mockMvc = MockMvcBuilders.standaloneSetup(hospitalController).build();
	}
	
	/**
	 * getAll hospitals 
	 * @throws Exception
	 */
	@Test
	public void testGetAll() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/hospitals"))
				.andExpect(MockMvcResultMatchers.status().is(200));
	}

}
