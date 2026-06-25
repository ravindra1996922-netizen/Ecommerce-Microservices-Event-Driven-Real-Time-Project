package com.productservice.microservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.productservice.microservices.entity.Product;

public interface ProductRepository  extends JpaRepository<Product,Integer >{

}
