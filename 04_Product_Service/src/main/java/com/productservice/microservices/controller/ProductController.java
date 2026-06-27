package com.productservice.microservices.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.productservice.microservices.dto.ProducttDto;
import com.productservice.microservices.reponse.ApiResponse;
import com.productservice.microservices.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ProductController {
	
	private final ProductService productService;
	
	@PostMapping("/addProduct")
	public ResponseEntity<ApiResponse<ProducttDto>>  addProduct(
			@RequestParam("product") String productJson,
			@RequestParam("file")   MultipartFile file) throws JsonMappingException, JsonProcessingException{
		
		ObjectMapper mapper= new ObjectMapper();
		ProducttDto producttDto = mapper.readValue(productJson, ProducttDto.class);
		
		ProducttDto producttDto2 = productService.addProduct(producttDto, file);
		
		ApiResponse<ProducttDto> response = new ApiResponse<>();
		response.setMsg("product added successfully");
		response.setStatusCode(201);
		response.setData(producttDto2);
		
		return new ResponseEntity<>(response,HttpStatus.CREATED);
		
	}

}
