package com.orderservice.microservices.dto;

import lombok.Data;

@Data
public class ProductDto {

	Integer productId;

	String productName;

	Double productPrice;

	String imageUrl;

	Integer quantity;

	String description;

	Integer rating;

	Integer categoryId;

}
