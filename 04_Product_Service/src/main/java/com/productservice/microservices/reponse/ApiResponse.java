package com.productservice.microservices.reponse;

import lombok.Data;

@Data
public class ApiResponse<T> {
	
	String msg;
	Integer statusCode;
	T data;
	

}
