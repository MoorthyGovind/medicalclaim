package com.hcl.medicalclaim.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UserDto {
	
	@NotBlank(message = "username is mandatory")
	@Email(message = "Invalid email address")
	private String userId;
	
	@NotBlank(message = "password is mandatory")
	private String password;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
