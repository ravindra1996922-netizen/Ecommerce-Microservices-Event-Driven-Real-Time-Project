package com.orderservice.microservices.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.orderservice.microservices.dto.OrderDto;
import com.orderservice.microservices.response.ApiResponse;
import com.orderservice.microservices.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class OrderController {
	
	private final OrderService orderService;
	@PostMapping("/placedOrder")
	public ResponseEntity<ApiResponse<OrderDto>> placedOrder(@RequestHeader ("X-CustomerId")Integer customerId){
		OrderDto placeOrder = orderService.placeOrder(customerId);
		 ApiResponse<OrderDto> apiResponse = ApiResponse.<OrderDto>builder().data(placeOrder).msg("order placed succesfully").statusCode(201).build();
		
		return new ResponseEntity<>(apiResponse,HttpStatus.CREATED);
		
	}

}
