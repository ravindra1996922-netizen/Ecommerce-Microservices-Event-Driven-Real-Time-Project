package com.orderservice.microservices.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;


@Entity
public class OrderEntity {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
Integer id;
Integer customerId;
Integer totalAmount;

@OneToMany(mappedBy = "orderEntity", cascade = CascadeType.ALL)
List<ProductEntity> products= new ArrayList<>();

Date orderPlacedAt;
String status;
Date orderUpdatedAt;
}
