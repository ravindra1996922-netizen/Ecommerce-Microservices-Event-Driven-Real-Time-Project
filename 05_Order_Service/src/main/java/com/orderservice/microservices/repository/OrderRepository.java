package com.orderservice.microservices.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orderservice.microservices.entity.OrderEntity;
import com.orderservice.microservices.entity.ProductEntity;

public interface OrderRepository  extends JpaRepository<OrderEntity,Integer>{


}
