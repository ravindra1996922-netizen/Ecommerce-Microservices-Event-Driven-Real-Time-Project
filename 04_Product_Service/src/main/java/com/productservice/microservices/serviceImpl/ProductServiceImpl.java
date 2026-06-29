package com.productservice.microservices.serviceImpl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.productservice.microservices.aws.AwsConfig;
import com.productservice.microservices.aws.AwsService;
import com.productservice.microservices.dto.PageResponseDto;
import com.productservice.microservices.dto.ProducttDto;
import com.productservice.microservices.entity.CategoryEntity;
import com.productservice.microservices.entity.ProductEntity;
import com.productservice.microservices.exception.ProductServiceException;
import com.productservice.microservices.mapper.ProductMapper;
import com.productservice.microservices.repository.CategoryRepository;
import com.productservice.microservices.repository.ProductRepository;
import com.productservice.microservices.service.ProductService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.services.s3.S3Client;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

	private final S3Client s3Client;
	private final AwsConfig awsConfig;
	private final ProductMapper productMapper;
	private final AwsService awsService;
	private final ProductRepository productRepository;
	private final CategoryRepository categoryRepository;

	@Transactional(rollbackOn = Exception.class)
	@Override
	public ProducttDto addProduct(ProducttDto producttDto, MultipartFile file) {

		Integer categoryId = producttDto.getCategoryId();
		Optional<CategoryEntity> optionalCategoryData = categoryRepository.findById(categoryId);
		if (optionalCategoryData.isEmpty()) {
			throw new ProductServiceException("category  is not present", HttpStatus.NOT_FOUND);
		}

		CategoryEntity categoryEntity = optionalCategoryData.get();

		ProductEntity productEntity = productMapper.convertIntoProductEntity(producttDto);
		productEntity.setCategoryEntity(categoryEntity);
		String imageUrl;
		try {
			imageUrl = awsService.saveFileInS3Bucket(file);
			productEntity.setImageUrl(imageUrl);
		} catch (AwsServiceException | SdkClientException | IOException e) {
			throw new ProductServiceException("Unable to save image on S3 Bucket ", HttpStatus.BAD_GATEWAY);
		}
		ProductEntity savedProductEntity = productRepository.save(productEntity);
		if (savedProductEntity == null) {
			awsService.deleteFileFromS3Bucket(imageUrl);
			throw new ProductServiceException("Product Not Saved ", HttpStatus.BAD_GATEWAY);
		}

		ProducttDto productDto = productMapper.convertIntoProductDto(savedProductEntity);
		return productDto;
	}
//=====================================================================================================

	@Override
	public PageResponseDto<ProducttDto> getAllProducts(Integer pageNum, Integer size, String sortBy, String sortDir) {

		if (pageNum == null || pageNum < 0) {
			throw new ProductServiceException("page num Should not be minus ", HttpStatus.BAD_REQUEST);
		}

		if (size == null || size < 1) {
			throw new ProductServiceException("size Should not be minus ", HttpStatus.BAD_REQUEST);
		}

		Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
		Pageable pageable = PageRequest.of(pageNum, size, sort);

		Page<ProductEntity> page = productRepository.findAll(pageable);

		PageResponseDto<ProducttDto> response = buildPageResponseDto(page);
		return response;

	}

//=====================================================================================================
	@Override
	public PageResponseDto<ProducttDto> searchProduct(Integer pageNum, Integer size, String search) {
		// TODO Auto-generated method stub
		Pageable pageable = PageRequest.of(pageNum, size);

		Page<ProductEntity> byProductNameContainingIgnoreCase = productRepository
				.findByProductNameContainingIgnoreCase(search, pageable);

		PageResponseDto<ProducttDto> response = buildPageResponseDto(byProductNameContainingIgnoreCase);
		return response;
	}

//=====================================================================================================

	@Override
	public ProducttDto updateProduct(Integer productId, ProducttDto producttDto, MultipartFile file) {

		Optional<ProductEntity> optionalProduct = productRepository.findById(productId);
		if (optionalProduct.isEmpty()) {
			throw new ProductServiceException("Product Not Found ", HttpStatus.BAD_REQUEST);
		}
		ProductEntity productEntity = optionalProduct.get();

		if (producttDto.getProductName() != null) {
			productEntity.setProductName(producttDto.getProductName());
		}
		if (producttDto.getDescription() != null) {
			productEntity.setDescription(producttDto.getDescription());
		}
		if (producttDto.getProductPrice() != null) {
			productEntity.setProductPrice(producttDto.getProductPrice());
		}
		if (productEntity.getQuantity() != null) {
			productEntity.setQuantity(producttDto.getQuantity());
		}
		try {

			awsService.deleteFileFromS3Bucket(productEntity.getImageUrl());

			String imageUrl = awsService.saveFileInS3Bucket(file);
			productEntity.setImageUrl(imageUrl);
		} catch (Exception e) {

			throw new ProductServiceException("file not saved in AWS", HttpStatus.BAD_GATEWAY);
		}

		ProductEntity savedProduct = productRepository.save(productEntity);

		return productMapper.convertIntoProductDto(productEntity);

	}

//=====================================================================================================
	@Override
	public String deleteProduct(Integer productId) {

		boolean existsById = productRepository.existsById(productId);
		if (!existsById) {
			throw new ProductServiceException("product not found", HttpStatus.BAD_REQUEST);
		}
		productRepository.deleteById(productId);

		return "Deleted Sucessfully";
	}

//=====================================================================================================	

	@Override
	public PageResponseDto<ProducttDto> getProductBycategory(Integer pageNum, Integer size, Integer categoryId) {

		Pageable pageable = PageRequest.of(pageNum, size);

		Page<ProductEntity> pageProduct = productRepository.findByCategoryEntityCategoryId(categoryId, pageable);
		List<ProductEntity> content = pageProduct.getContent();

		if (content.isEmpty()) {
			throw new ProductServiceException("category not present", HttpStatus.BAD_REQUEST);
		}

		PageResponseDto<ProducttDto> response = buildPageResponseDto(pageProduct);

		return response;
	}

//=====================================================================================================
	private PageResponseDto<ProducttDto> buildPageResponseDto(Page<ProductEntity> productPage) {

		List<ProducttDto> list = productPage.getContent().stream()
				.map(data -> productMapper.convertIntoProductDto(data)).toList();

		return PageResponseDto.<ProducttDto>builder().currentPageNumber(productPage.getNumber())
				.sizeOfDataOnload(productPage.getSize()).totalProducts(productPage.getTotalElements())
				.totalPages(productPage.getTotalPages()).firstPage(productPage.isFirst()).lastPage(productPage.isLast())
				.data(list).build();

	}

}
