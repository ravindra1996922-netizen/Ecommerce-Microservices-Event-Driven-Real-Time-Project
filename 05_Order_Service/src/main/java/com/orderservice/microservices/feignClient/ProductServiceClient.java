package com.orderservice.microservices.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.orderservice.microservices.dto.CartItemDto;
import com.orderservice.microservices.response.ApiResponse;

@FeignClient(name = "PRODUCT-SERVICE")
public interface ProductServiceClient {

	@GetMapping("/getproductbyId/{productId}")
	public ApiResponse<CartItemDto> getProductById(@PathVariable("productId") Integer productId);

}