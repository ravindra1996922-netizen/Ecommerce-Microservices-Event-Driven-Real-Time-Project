package com.orderservice.microservices.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.orderservice.microservices.dto.CartItemDto;
import com.orderservice.microservices.entity.CartItemEntity;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CartMapper {

	private final ModelMapper modelMapper;

	public CartItemDto convertIntoCartItemDto(CartItemEntity cartItemEntity) {

		CartItemDto cartItemDto = modelMapper.map(cartItemEntity, CartItemDto.class);
		return cartItemDto;
	}

	public CartItemEntity convertIntoCartEntity(CartItemDto cartItemDto) {

		return modelMapper.map(cartItemDto, CartItemEntity.class);
	}

}
