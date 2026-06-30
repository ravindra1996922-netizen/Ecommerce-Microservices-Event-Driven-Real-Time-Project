	package com.orderservice.microservices.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class CartItemEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer cartId;
	
	@Column(nullable =  false)
	Integer customerId;
	
	@Column(nullable = false)
	Integer productId; 
	
	String productName;
	
	Double productPrice;
	
	Integer quantity;
	
	String imageUrl;
	
	
	
	

}
