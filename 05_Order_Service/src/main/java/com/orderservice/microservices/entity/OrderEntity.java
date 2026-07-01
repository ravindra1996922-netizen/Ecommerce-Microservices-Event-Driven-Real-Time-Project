package com.orderservice.microservices.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString(exclude = "products")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;

	@Column(nullable = false)
	Integer customerId;

	@Column(nullable = false)
	Double totalAmount;

	@CreationTimestamp
	@Column(updatable = false)
	Date orderPlacedAt;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	OrderStatus status;

	@UpdateTimestamp
	@Column(insertable = false)
	Date orderUpdatedAt;

	@OneToMany(mappedBy = "orderEntity", cascade = CascadeType.ALL)
	List<OrderItemsEntity> products = new ArrayList<>();
}
