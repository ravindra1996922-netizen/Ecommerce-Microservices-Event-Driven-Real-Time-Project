package com.productservice.microservices.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.productservice.microservices.config.ConfigClass;
import com.productservice.microservices.dto.CategoryDto;
import com.productservice.microservices.reponse.ApiResponse;
import com.productservice.microservices.service.CategoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/product")
@Slf4j
@RequiredArgsConstructor
public class CategoryController {

	private final CategoryService categoryService;

	@PostMapping("/addcategory")
	public ResponseEntity<ApiResponse<CategoryDto>> addCategory(@Valid @RequestBody CategoryDto categoryDTO) {

		CategoryDto savedCategory = categoryService.addCategory(categoryDTO);

		ApiResponse<CategoryDto> response = ApiResponse.<CategoryDto>builder().msg("CATEGORY_ADDED").statusCode(201)
				.data(savedCategory).build();

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
//====================================================================================================================

	@PutMapping("/category/{categoryId}")
	public ResponseEntity<ApiResponse<CategoryDto>> updateCategory(@PathVariable Integer categoryId,
			@Valid @RequestBody CategoryDto categoryDTO) {

		CategoryDto updatedCategory = categoryService.updateCategory(categoryId, categoryDTO);

		ApiResponse<CategoryDto> response = ApiResponse.<CategoryDto>builder().msg("CATEGORY_UPDATED").statusCode(200)
				.data(updatedCategory).build();

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
//=========================================================================================================================

	@GetMapping("/category")
	public ResponseEntity<ApiResponse<List<CategoryDto>>> getAllCategories() {

		List<CategoryDto> categories = categoryService.getAllCategories();

		ApiResponse<List<CategoryDto>> response = ApiResponse.<List<CategoryDto>>builder().msg("Categories fetched")
				.statusCode(200).data(categories).build();

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
//=======================================================================================================================

	@GetMapping("/category/{categoryId}")
	public ResponseEntity<ApiResponse<CategoryDto>> getCategoryById(@PathVariable Integer categoryId) {

		CategoryDto category = categoryService.getCategoryById(categoryId);

		ApiResponse<CategoryDto> response = ApiResponse.<CategoryDto>builder().msg("Category fetched").statusCode(200)
				.data(category).build();

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

//======================================================================================================================

	@DeleteMapping("/category/{categoryId}")
	public ResponseEntity<ApiResponse<CategoryDto>> deleteCategoryById(@PathVariable Integer categoryId) {

		CategoryDto deletedCategory = categoryService.deleteCategoryById(categoryId);

		ApiResponse<CategoryDto> response = ApiResponse.<CategoryDto>builder().msg("CATEGORY_DELETED").statusCode(200)
				.data(deletedCategory).build();

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
