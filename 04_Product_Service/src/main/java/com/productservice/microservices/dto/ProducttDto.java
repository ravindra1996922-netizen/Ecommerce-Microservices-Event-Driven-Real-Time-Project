package com.productservice.microservices.dto;

import java.util.Locale.Category;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProducttDto {

	Integer productId;
	
	@NotBlank(message = "product name should not blank")
	String productName;

	@NotNull
	@Min(value = 1)
	Double productPrice;

	String imageUrl;

	@NotNull
	@Min(value = 0)
	Integer quantity;

	String description;

	Integer rating;

	@NotNull
	Integer categoryId;

}
