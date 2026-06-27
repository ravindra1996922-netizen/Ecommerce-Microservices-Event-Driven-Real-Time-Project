package com.productservice.microservices.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.productservice.microservices.dto.ProducttDto;
import com.productservice.microservices.entity.ProductEntity;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductMapper {

	private final ModelMapper modelMapper;

	public ProductEntity convertIntoProductEntity(ProducttDto producttDto) {
		return modelMapper.map(producttDto, ProductEntity.class);

	}
	
	public ProducttDto convertIntoProductDto(ProductEntity productEntity) {
		return modelMapper.map(productEntity, ProducttDto.class);

	}

}
