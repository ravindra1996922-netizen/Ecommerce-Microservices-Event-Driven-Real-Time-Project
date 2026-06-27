package com.productservice.microservices.exception;

import lombok.Data;

@Data
public class ExceptionResponse <T>{
	
	private String errMsg;
	private String errCode;
	T data;
	

}
