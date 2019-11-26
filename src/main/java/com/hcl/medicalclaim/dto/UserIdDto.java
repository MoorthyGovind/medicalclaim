package com.hcl.medicalclaim.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UserIdDto {
	
	@NotBlank(message = "username is mandatory")
	@Email(message = "Invalid email address")
	private String userId;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
}
