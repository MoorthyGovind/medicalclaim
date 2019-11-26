package com.hcl.medicalclaim.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hcl.medicalclaim.dto.UserDto;
import com.hcl.medicalclaim.entity.User;
import com.hcl.medicalclaim.repository.UserRepository;


@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceImplTest {

	@InjectMocks
	UserServiceImpl userServiceImpl;
	
	@Mock
	UserRepository userRepository;

    User user = new User();
	@Before
	public void init() {
	        MockitoAnnotations.initMocks(this);
	     user.setId(1L);
	     user.setUserId("moorthy127@gmail.com");
	     user.setPassword("Start@123");
	     user.setApprovalLevel(1L);
	     user.setCreatedBy("Admin");
	     user.setCreatedDate(new Date());
	     user.setStatus(true);
	}
	 
	@Test
	public void testGetAll() throws Exception {		
		List<User> expectedUsers = Arrays.asList(user); 
		//return mocked result set on find
	    when(userRepository.findAll()).thenReturn(expectedUsers);
        List<User> findAll = userServiceImpl.getUsers();
        assertThat(findAll).hasSize(1);
	}
	
	@Test
	public void testLoginCheck() throws Exception{		
		UserDto userDto = new UserDto();
		userDto.setUserId("moorthy127@gmail.com");
		userDto.setPassword("start@123");
		when(userRepository.findByUserIdAndPassword("moorthy127@gmail.com", "start@123")).thenReturn(user);

		User userDetail = userServiceImpl.loginCheck(userDto);
        assertEquals(user.getUserId(), userDetail.getUserId());
        assertEquals(user.getStatus(), userDetail.getStatus());

	}
}
