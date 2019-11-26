package com.hcl.medicalclaim.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.request.WebRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcl.medicalclaim.common.AppConstants;
import com.hcl.medicalclaim.dto.UserDto;
import com.hcl.medicalclaim.entity.User;
import com.hcl.medicalclaim.exception.CustomExceptionHandler;
import com.hcl.medicalclaim.response.ApiResponse;
import com.hcl.medicalclaim.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserControllerTest{

	@InjectMocks
	UserController userController;
	
	@Mock
	UserService userService;
	
	private MockMvc mockMvc;

	User user = new User();

	@Before
	public void init() {
		mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
		user.setUserId("moorthy127@gmail.com");
		user.setPassword("start@123");
	}
	
	/**
	 * test all get users  
	 * @throws Exception
	 */
	@Test
	public void testGetAllUsers() throws Exception{
		
		mockMvc.perform(MockMvcRequestBuilders.get("/users"))
		 		.andExpect(MockMvcResultMatchers.status().is(200));
		verify(userService,times(1)).getUsers();
	}
	
	/**
	 * test user login with userId and password
	 * @throws Exception
	 */
	@Test
	public void testCheckLogin() throws Exception{
		UserDto userDto = new UserDto();
		userDto.setUserId("moorthy127@gmail.com");
		userDto.setPassword("start@123");
		
		when(userService.loginCheck(userDto)).thenReturn(user);
		
		ApiResponse user = userController.loginCheck(userDto);
		assertEquals(200, user.getStatusCode());
	}
	
	@Test
	public void testInvalidCheckLogin() throws Exception{
		
		UserDto userDto = new UserDto();
		userDto.setUserId("moorthy127@gmail.com");
		userDto.setPassword("start");

		when(userService.loginCheck(userDto)).thenReturn(null);
		
		ApiResponse response = userController.loginCheck(userDto);
		assertEquals(AppConstants.FAILURE, response.getStatus());
	}
	
	@Test
	public void testInvalidData() throws Exception{
		
		UserDto userDto = new UserDto();
		userDto.setUserId("moorthy");
		userDto.setPassword("start");

		//MvcResult for mockmvc performed
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/login-check")
                .content(asJsonString(userDto))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andReturn();
			WebRequest webrequest = null;
			new CustomExceptionHandler().handleException(result.getResolvedException(), webrequest);
	}
	

	/**
	 * 
	 * @param obj
	 * @return
	 */
	public static String asJsonString(final Object obj) throws Exception {
            return new ObjectMapper().writeValueAsString(obj);
    }
}
