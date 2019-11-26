package com.hcl.medicalclaim.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.medicalclaim.dto.UserDto;
import com.hcl.medicalclaim.entity.User;
import com.hcl.medicalclaim.repository.UserRepository;
import com.hcl.medicalclaim.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserRepository userRepository;
	
	/**
	 * get all the users
	 */
	@Override
	public List<User> getUsers(){
			return userRepository.findAll();
	}
	
	/**user login check with userId and password
	 * @UserDto userDto
	 */
	@Override
	public User loginCheck(UserDto userDto) 
	{ 
		return userRepository.findByUserIdAndPassword(userDto.getUserId(), userDto.getPassword()); 
	}
}
