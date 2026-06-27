package com.productservice.microservices.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(exception= ProductServiceException.class)
	
	public ResponseEntity<ExceptionResponse> handleProductServiceException(ProductServiceException productServiceException){
		ExceptionResponse exceptionResponse =new  ExceptionResponse();
		exceptionResponse.setErrMsg(productServiceException.getErrMsg());
		exceptionResponse.setErrCode(String.valueOf(productServiceException.getHttpStatus()));
		
		
		return new ResponseEntity<>(exceptionResponse,productServiceException.getHttpStatus());
	}
	

}
