package com.orderservice.microservices.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class OrderItemsEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;

	@Column(nullable = false)
	Integer productId;

	@Column(nullable = false)
	String productName;

	@Column(nullable = false)
	Double productPrice;

	@Column(nullable = false)
	Integer productQuantity;

	@Column(nullable = false)
	String imageUrl;
	
	@Column(nullable = false)
	Double totalPrice;

	@ManyToOne
	@JoinColumn(name = "orderId")
	OrderEntity orderEntity;
}
