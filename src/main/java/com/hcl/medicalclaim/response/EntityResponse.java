package com.hcl.medicalclaim.response;

public class EntityResponse<T> {
	
	private String status;
	private String message;
	private T data;
		
	public EntityResponse(String status, String message, T obj){
		this.status = status;
		this.message = message;
		this.data = obj;
	}
	
	public String getStatus() {
		return status;
	}
	
	public String getMessage() {
		return message;
	}
	
	public T getData() {
		return data;
	}
}
