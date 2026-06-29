package com.productservice.microservices.entity;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@ToString( exclude ="categoryEntity" )
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer productId;
	
	@Column(nullable = false)
	String productName;
	
	@Column(nullable = false)
	Double productPrice;
	
	@Column(nullable = false)
	String imageUrl;
	
	@Column(nullable = false)
	Integer quantity;

	@Column(nullable = false)
	String description;
	
	Integer rating;

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_Id",nullable = false)
	CategoryEntity  categoryEntity;
	
	@CreationTimestamp
	@Column(updatable = false)
	LocalDate createdDate;
	
	
	@UpdateTimestamp
	@Column(insertable  = false)
	LocalDate updateDate;

}
