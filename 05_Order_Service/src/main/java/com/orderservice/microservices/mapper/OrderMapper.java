package com.orderservice.microservices.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.orderservice.microservices.dto.OrderDto;
import com.orderservice.microservices.entity.OrderEntity;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Component
public class OrderMapper {

	private final ModelMapper modelMapper;

	public OrderDto convetIntoOrderDto(OrderEntity orderEntity) {

		return modelMapper.map(orderEntity, OrderDto.class);
	}

	public OrderEntity convetIntoOrderEntity(OrderDto orderDto) {

		return modelMapper.map(orderDto, OrderEntity.class);
	}

}
