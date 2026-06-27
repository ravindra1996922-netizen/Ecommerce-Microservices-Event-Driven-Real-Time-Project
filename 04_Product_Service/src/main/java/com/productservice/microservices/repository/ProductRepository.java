package com.productservice.microservices.repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

import org.hibernate.query.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import com.productservice.microservices.entity.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {

	Optional< List<ProductEntity>> findByProductName (String name);

	Optional<List<ProductEntity>> findByCategoryEntityCategoryId(Integer categoryId);
	
	
	

}
