package com.productservice.microservices.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.productservice.microservices.entity.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {

	   Optional<CategoryEntity> findBycategoryName(String categoryName);
	   
	   boolean existsByCategoryName(String category);
	   
	   
}
