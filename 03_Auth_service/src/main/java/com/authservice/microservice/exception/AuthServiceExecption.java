package com.authservice.microservice.exception;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class AuthServiceExecption extends RuntimeException {
	
	String errcode;
	
	HttpStatus status;
	
	public AuthServiceExecption(String msg, HttpStatus status,String errcode) {
		super(msg);
		this.status = status;
		this.errcode=errcode;
	}
	

}
