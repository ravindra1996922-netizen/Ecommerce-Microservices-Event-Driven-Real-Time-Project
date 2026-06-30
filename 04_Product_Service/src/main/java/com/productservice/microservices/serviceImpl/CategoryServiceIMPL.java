package com.productservice.microservices.serviceImpl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.productservice.microservices.dto.CategoryDto;
import com.productservice.microservices.entity.CategoryEntity;
import com.productservice.microservices.exception.ProductServiceException;
import com.productservice.microservices.mapper.CategoryMapper;
import com.productservice.microservices.repository.CategoryRepository;
import com.productservice.microservices.service.CategoryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceIMPL implements CategoryService {

	private final CategoryRepository categoryRepository;
	private final CategoryMapper categoryMapper;

	@Override
	public CategoryDto addCategory(CategoryDto categoryDto) {

		if (categoryRepository.existsByCategoryName(categoryDto.getCategoryName())) {

			throw new ProductServiceException("Category Already Exist", HttpStatus.CONFLICT);
		}
		CategoryEntity category = categoryMapper.toEntity(categoryDto);

		CategoryEntity savedCategory = categoryRepository.save(category);
		log.info(savedCategory+"  cat");

		return categoryMapper.toDTO(savedCategory);
	}

	@Override
	public CategoryDto updateCategory(Integer categoryId, CategoryDto categoryDto) {

		CategoryEntity existingCategory = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ProductServiceException("categoryId Not  incorrect", HttpStatus.BAD_REQUEST));

		existingCategory.setCategoryName(categoryDto.getCategoryName());

		CategoryEntity updatedCategory = categoryRepository.save(existingCategory);

		return categoryMapper.toDTO(updatedCategory);
	}

	@Override
	public List<CategoryDto> getAllCategories() {

		log.info("Getting all categories");

		List<CategoryEntity> categories = categoryRepository.findAll();

		return categories.stream().map(data -> categoryMapper.toDTO(data)).toList();

	}

	@Override
	public CategoryDto getCategoryById(Integer categoryId) {

		CategoryEntity category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ProductServiceException("categoryId Not present", HttpStatus.BAD_REQUEST));

		return categoryMapper.toDTO(category);
	}

	@Override
	public CategoryDto deleteCategoryById(Integer categoryId) {

		CategoryEntity category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ProductServiceException("category not found", HttpStatus.BAD_REQUEST));

		categoryRepository.deleteById(categoryId);

		return categoryMapper.toDTO(category);
	}

}
