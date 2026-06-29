package com.productservice.microservices.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.productservice.microservices.dto.PageResponseDto;
import com.productservice.microservices.dto.ProducttDto;

public interface ProductService {

//	public PageResponseDto<ProducttDto> getAllProduct(Integer page,Integer size,String sortBy,String sortDirection);

	public ProducttDto addProduct(ProducttDto producttDto, MultipartFile file);

	public ProducttDto updateProduct(Integer productId,ProducttDto producttDto, MultipartFile file);

	public String deleteProduct(Integer productId);

	public PageResponseDto<ProducttDto> getAllProducts(Integer pageNum, Integer size, String sortBy, String sortDir);
	
	public PageResponseDto<ProducttDto> searchProduct(Integer pageNum, Integer size,String search);

	public PageResponseDto<ProducttDto> getProductBycategory(Integer pageNum, Integer size, Integer categoryId);
}
