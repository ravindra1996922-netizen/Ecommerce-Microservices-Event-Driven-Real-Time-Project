package com.productservice.microservices.exception;

import org.springframework.http.HttpStatus;

import lombok.Data;
@Data
public class ProductServiceException extends RuntimeException {

	String errMsg;
	HttpStatus httpStatus;
	public ProductServiceException(String errMsg, HttpStatus httpStatus) {
		super(errMsg);
		this.errMsg = errMsg;
		this.httpStatus = httpStatus;
	}
	
	
}
