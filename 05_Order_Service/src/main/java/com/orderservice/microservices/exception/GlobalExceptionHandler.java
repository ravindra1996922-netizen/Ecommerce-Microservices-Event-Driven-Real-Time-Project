package com.orderservice.microservices.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(exception = OrderServiceException.class)
	public ResponseEntity<ExceptionResponse<OrderServiceException>> exceptionHandeler(
			OrderServiceException orderServiceException) {

		ExceptionResponse<OrderServiceException> errorResponse = new ExceptionResponse<>();
		errorResponse.setErrMsg(orderServiceException.getErrMsg());
		return new ResponseEntity<>(errorResponse, orderServiceException.getHttpStatus());

	}
	
	@ExceptionHandler(exception = MethodArgumentNotValidException.class)
	public ResponseEntity<ExceptionResponse> validationExceptionHandeler(
			MethodArgumentNotValidException notValidException) {
		BindingResult bindingResult = notValidException.getBindingResult();
		List<FieldError> fieldErrors = bindingResult.getFieldErrors();

		Map<String, Object> result = new HashMap<>();
		for (FieldError er : fieldErrors) {

			result.put(er.getField(), er.getDefaultMessage());

		}

		ExceptionResponse exceptionResponse = ExceptionResponse.builder().data(result)
				.build();
		
		return new ResponseEntity<>(exceptionResponse,HttpStatus.BAD_REQUEST);
	}

}
