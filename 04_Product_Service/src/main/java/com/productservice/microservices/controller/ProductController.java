package com.productservice.microservices.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.productservice.microservices.dto.PageResponseDto;
import com.productservice.microservices.dto.ProducttDto;
import com.productservice.microservices.reponse.ApiResponse;
import com.productservice.microservices.service.ProductService;

import jakarta.ws.rs.PathParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/product") 
@RequiredArgsConstructor
@Slf4j
public class ProductController {

	private final ProductService productService;

	@PostMapping("/addProduct")
	public ResponseEntity<ApiResponse<ProducttDto>> addProduct(@RequestParam("product") String productJson,
			@RequestParam("file") MultipartFile file) throws JsonMappingException, JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();
		ProducttDto producttDto = mapper.readValue(productJson, ProducttDto.class);

		ProducttDto producttDto2 = productService.addProduct(producttDto, file);

		ApiResponse<ProducttDto> response = new ApiResponse<>();
		response.setMsg("product added successfully");
		response.setStatusCode(201);
		response.setData(producttDto2);

		return new ResponseEntity<>(response, HttpStatus.CREATED);

	}

//===============================================================================================
	@GetMapping("/getproductbyId/{productId}")
	public ResponseEntity<ApiResponse<ProducttDto>> getProductById(@PathVariable("productId") Integer productId) {

		ProducttDto producttDto = productService.getProductById(productId);

		ApiResponse<ProducttDto> apiResponse = ApiResponse.<ProducttDto>builder().data(producttDto)
				.msg("found sucessFully").statusCode(200).build();

		return new ResponseEntity<>(apiResponse, HttpStatus.OK);

	}

//==================================================================================================
	@GetMapping("/getAllProducts")
	public ResponseEntity<ApiResponse<PageResponseDto<ProducttDto>>> getAllProducts(
			@RequestParam(defaultValue = "0") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize,
			@RequestParam(defaultValue = "productId") String sortBy,
			@RequestParam(defaultValue = "asc") String sortDir) {

		PageResponseDto<ProducttDto> searchByProductName = productService.getAllProducts(pageNum, pageSize, sortBy,
				sortDir);
		ApiResponse<PageResponseDto<ProducttDto>> apiResponse = ApiResponse.<PageResponseDto<ProducttDto>>builder()
				.msg("search sucessfull done").statusCode(302).data(searchByProductName).build();
		return new ResponseEntity<>(apiResponse, HttpStatus.FOUND);

	}

//==================================================================================================

	@GetMapping("/search")
	public ResponseEntity<ApiResponse<PageResponseDto<ProducttDto>>> searchProduct(@RequestParam Integer pageNum,
			@RequestParam Integer size, @RequestParam String productName) {

		log.info(productName + " search product controller");
		PageResponseDto<ProducttDto> searchProduct = productService.searchProduct(pageNum, size, productName);
		ApiResponse<PageResponseDto<ProducttDto>> apiResponse = ApiResponse.<PageResponseDto<ProducttDto>>builder()
				.data(searchProduct).msg("products fetch successfully").statusCode(302).build();
		return new ResponseEntity<>(apiResponse, HttpStatus.FOUND);
	}

//=========================================================================================================
	@PutMapping("/updateProduct")
	public ResponseEntity<ApiResponse<ProducttDto>> updateProduct(@RequestParam("productId") Integer productId,
			@RequestParam("productDtoJson") String productDtoJson, @RequestParam(required = false,name = "file") MultipartFile file)
			throws JsonMappingException, JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();
		ProducttDto productDto = mapper.readValue(productDtoJson, ProducttDto.class);
		ProducttDto updateProduct = productService.updateProduct(productId, productDto, file);

		ApiResponse<ProducttDto> apiResponse = ApiResponse.<ProducttDto>builder().data(updateProduct)
				.msg("updated succesfully").statusCode(200).build();
		return new ResponseEntity<ApiResponse<ProducttDto>>(apiResponse, HttpStatus.OK);
	}
//================================================================================================

	@DeleteMapping("/deleteProduct")
	public ResponseEntity<ApiResponse<String>> deleteProduct(@RequestParam Integer productId) {
		String deleteProduct = productService.deleteProduct(productId);

		ApiResponse<String> apiResponse = ApiResponse.<String>builder().msg(" product deleted").statusCode(200)
				.data(deleteProduct).build();

		return new ResponseEntity<ApiResponse<String>>(apiResponse, HttpStatus.OK);
	}
//=========================================================================================================

	@GetMapping("/getProductByCategory")
	public ResponseEntity<ApiResponse<PageResponseDto<ProducttDto>>> getProductBycategory(@RequestParam Integer pageNum,
			@RequestParam Integer size, @RequestParam Integer categoryId) {

		PageResponseDto<ProducttDto> productBycategory = productService.getProductBycategory(pageNum, size, categoryId);

		ApiResponse<PageResponseDto<ProducttDto>> apiResponse = ApiResponse.<PageResponseDto<ProducttDto>>builder()
				.msg("fetch product by category ").statusCode(302).data(productBycategory).build();

		return new ResponseEntity<ApiResponse<PageResponseDto<ProducttDto>>>(apiResponse, HttpStatus.FOUND);
	}

}
