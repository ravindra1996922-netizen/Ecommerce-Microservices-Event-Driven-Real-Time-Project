package com.orderservice.microservices.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductServiceResponseDto {

	
	String msg;
	Integer statusCode;
	ProductDto data;

}
