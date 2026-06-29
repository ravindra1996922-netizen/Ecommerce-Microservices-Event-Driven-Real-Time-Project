package com.productservice.microservices.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.productservice.microservices.entity.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {

//	Optional< List<ProductEntity>> findByProductName (String name);

	Page<ProductEntity> findByProductNameContainingIgnoreCase(String productName, Pageable pageable);

	Page<ProductEntity> findByCategoryEntityCategoryId(Integer categoryId, Pageable pageable);

}
