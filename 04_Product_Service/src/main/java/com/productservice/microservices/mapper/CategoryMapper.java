package com.productservice.microservices.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.productservice.microservices.dto.CategoryDto;
import com.productservice.microservices.entity.CategoryEntity;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CategoryMapper {

	private final ModelMapper modelMapper;

	public CategoryDto toDTO(CategoryEntity category) {
		return modelMapper.map(category, CategoryDto.class);
	}

	public CategoryEntity toEntity(CategoryDto categoryDTO) {
		return modelMapper.map(categoryDTO, CategoryEntity.class);
	}

}
