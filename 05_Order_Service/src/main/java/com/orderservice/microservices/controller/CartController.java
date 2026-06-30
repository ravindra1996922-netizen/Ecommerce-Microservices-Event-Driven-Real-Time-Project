package com.orderservice.microservices.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.orderservice.microservices.dto.CartItemDto;
import com.orderservice.microservices.dto.CartRequestDto;
import com.orderservice.microservices.response.ApiResponse;
import com.orderservice.microservices.service.CartService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CartController {

	private final CartService cartService;

	@PostMapping("/addcart")
	public ResponseEntity<ApiResponse<CartItemDto>> addProduct(@RequestHeader("X-CustomerId") Integer customerId,
			@RequestBody CartRequestDto cartRequestDto) {

		log.info(cartRequestDto.getProductId()+"cartController"+customerId);
		CartItemDto cartItemDto = cartService.addcart(customerId, cartRequestDto);
		ApiResponse<CartItemDto> apiResponse = ApiResponse.<CartItemDto>builder().data(cartItemDto).statusCode(201)
				.msg("item added in cart sucessfully").build();
		return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
	}
}
