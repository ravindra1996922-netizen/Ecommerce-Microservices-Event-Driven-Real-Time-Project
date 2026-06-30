package com.orderservice.microservices.exception;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class OrderServiceException  extends RuntimeException{
	
	String errMsg;
	HttpStatus httpStatus;
	public OrderServiceException(String errMsg, HttpStatus httpStatus) {
		super(errMsg);
		this.errMsg = errMsg;
		this.httpStatus = httpStatus;
	}
	
	

}
