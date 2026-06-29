package com.orderservice.microservices.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class ProductEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	String productId;
	Double productPrice;
	Integer productQuantity;
	@ManyToOne
	@JoinColumn(name="order_id")
	OrderEntity orderEntity;
}
