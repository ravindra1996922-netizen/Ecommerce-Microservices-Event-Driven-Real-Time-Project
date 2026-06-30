package com.orderservice.microservices.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartRequestDto {
	
	@NotNull
	Integer productId;
	
	@NotNull
	@Min(1)
	Integer productQuantity;
	

}
