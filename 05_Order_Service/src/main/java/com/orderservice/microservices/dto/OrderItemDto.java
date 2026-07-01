
package com.orderservice.microservices.dto;

import lombok.Data;

@Data
public class OrderItemDto {
	
	
	Integer OrderItemId;
	
	Integer productId;
	
	String productName;
	
	Double productPrice;

	String imageUrl;

	Integer quantity;
	
	Double totalPrice;
	
	

	
	
	
	

}
