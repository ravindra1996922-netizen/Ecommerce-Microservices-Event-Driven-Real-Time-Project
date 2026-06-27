package com.productservice.microservices.serviceImpl;

import java.io.IOException;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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



	@Override
	public ProducttDto updateProduct(ProducttDto producttDto, MultipartFile file) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProducttDto deleteProduct(Integer productId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageResponseDto<ProducttDto> getAllProduct(Integer page, Integer size, String sortBy, String sortDirection) {
		// TODO Auto-generated method stub
		return null;
	}

}
