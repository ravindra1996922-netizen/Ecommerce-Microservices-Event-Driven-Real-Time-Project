package com.orderservice.microservices.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orderservice.microservices.entity.CartItemEntity;

public interface CartRepository extends JpaRepository<CartItemEntity, Integer> {

	List<CartItemEntity> findByCustomerId(Integer customerId);

	CartItemEntity findByProductIdAndCustomerId(Integer productId, Integer customerId);

	void deleteByCustomerId(Integer customerId);

	void deleteByCartId(Integer cartId);

	boolean existsById(Integer customerId);
}