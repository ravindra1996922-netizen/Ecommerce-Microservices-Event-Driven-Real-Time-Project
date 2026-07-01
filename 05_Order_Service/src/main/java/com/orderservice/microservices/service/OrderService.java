package com.orderservice.microservices.service;

import java.util.List;

import com.orderservice.microservices.dto.OrderDto;
import com.orderservice.microservices.dto.OrderItemDto;

public interface OrderService {

	
	public OrderDto placeOrder(Integer customerId);
	
	public  List<OrderDto> getAllorderDetails(Integer CustomerId);
	
	public OrderDto UpadteOrderStatus(Integer orderId);
	 
}
