package com.productservice.microservices.service;

import org.springframework.web.multipart.MultipartFile;

import com.productservice.microservices.dto.PageResponseDto;
import com.productservice.microservices.dto.ProducttDto;

public interface ProductService {

	public PageResponseDto<ProducttDto> getAllProduct(Integer page,Integer size,String sortBy,String sortDirection);
	
	public ProducttDto addProduct(ProducttDto producttDto,MultipartFile file);
	
	public ProducttDto updateProduct(ProducttDto producttDto,MultipartFile file);
	
	public ProducttDto deleteProduct(Integer productId);
	
}
