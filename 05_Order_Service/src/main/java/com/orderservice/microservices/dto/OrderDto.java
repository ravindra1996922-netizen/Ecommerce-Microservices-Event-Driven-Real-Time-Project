package com.orderservice.microservices.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderDto {

	Integer orderId;
	Integer customerId;
	String customerEmail;
	String status;
	Double totalAmount;
	LocalDate orderCreatedDate;
	LocalDate statusUpdateddate;
	List<OrderItemDto> orderItems;

}
