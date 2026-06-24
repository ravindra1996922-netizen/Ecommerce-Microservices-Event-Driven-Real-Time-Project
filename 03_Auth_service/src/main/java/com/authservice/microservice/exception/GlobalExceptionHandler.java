package com.authservice.microservice.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.catalina.loader.ResourceEntry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.authservice.microservice.response.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(exception = AuthServiceExecption.class)
	public ResponseEntity<ExceptionResponse> handelException(AuthServiceExecption authServiceExecption) {
		
		ExceptionResponse response = ExceptionResponse.builder().errCode(authServiceExecption.getErrcode()).errMsg(authServiceExecption.getMessage()).build();
		

		return new ResponseEntity<>(response,authServiceExecption.status);
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

		ExceptionResponse exceptionResponse = ExceptionResponse.builder().errCode("400").errMsg("user not created").data(result)
				.build();
		
		return new ResponseEntity<>(exceptionResponse,HttpStatus.BAD_REQUEST);
	}
}
