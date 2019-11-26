package com.hcl.medicalclaim.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.medicalclaim.service.UserService;
import com.hcl.medicalclaim.common.AppConstants;
import com.hcl.medicalclaim.dto.UserDto;
import com.hcl.medicalclaim.entity.User;
import com.hcl.medicalclaim.response.ApiResponse;

@RestController
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserService userService;
	
	/**
	 * getAllUsers for user lists
	 * @return
	 */
	@GetMapping(value="/users")
	public ResponseEntity<List<User>> getAllUsers(){
		logger.info("getAllUsers...");
		List<User> users = userService.getUsers();
		return new ResponseEntity<>(users, HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param usetDto
	 * @return
	 */
	@PostMapping(value="/login-check", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponse loginCheck(@Valid @RequestBody UserDto usetDto){
	 ApiResponse response;
	 User userLogin = userService.loginCheck(usetDto);

	 // Check isAuthorized User or not.
	 Optional<User> isAuthorizedUser = Optional.ofNullable(userLogin);
	 if(isAuthorizedUser.isPresent()){
		 response = new ApiResponse(AppConstants.SUCCESS, HttpStatus.OK.value(), 
				 AppConstants.SUCCESS_MESSAGE);
	 }else {
		 response = new ApiResponse(AppConstants.FAILURE, HttpStatus.UNPROCESSABLE_ENTITY.value(), 
				 AppConstants.INVALID_USERNAME);
	 }
	 
	 return response;
	}
}
