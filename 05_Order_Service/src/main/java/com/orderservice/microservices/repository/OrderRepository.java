package com.orderservice.microservices.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orderservice.microservices.entity.OrderEntity;

public interface OrderRepository  extends JpaRepository<OrderEntity,Integer>{
	
	
            List<OrderEntity> findByCustomerIdOrderByOrderPlacedAtDesc(Integer customerId);

}
