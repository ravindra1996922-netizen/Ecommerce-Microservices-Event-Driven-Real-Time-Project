	package com.orderservice.microservices.dto;
	
	import lombok.AllArgsConstructor;
	import lombok.Builder;
	import lombok.Data;
	import lombok.NoArgsConstructor;
	
	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public class CartItemDto {
	
		Integer cartId;
		Integer productId;
		Integer customerId;
	
		String productName;
	
		Double productPrice;
	
		String imageUrl;
	
		Integer quantity;
	
		String description;
	
		Integer rating;
	
		Integer categoryId;
	
	}
