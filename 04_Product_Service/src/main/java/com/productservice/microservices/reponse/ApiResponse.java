package com.productservice.microservices.reponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder

@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
	
	String msg;
	Integer statusCode;
	T data;
	

}
