package com.hcl.medicalclaim.service;

import java.util.List;

import com.hcl.medicalclaim.dto.UserDto;
import com.hcl.medicalclaim.entity.User;


public interface UserService {
	public List<User> getUsers();

	public User loginCheck(UserDto userDto);
}
