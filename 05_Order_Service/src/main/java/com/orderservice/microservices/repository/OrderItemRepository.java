package com.orderservice.microservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orderservice.microservices.entity.OrderItemsEntity;

public interface OrderItemRepository extends JpaRepository<OrderItemsEntity, Integer> {

}
