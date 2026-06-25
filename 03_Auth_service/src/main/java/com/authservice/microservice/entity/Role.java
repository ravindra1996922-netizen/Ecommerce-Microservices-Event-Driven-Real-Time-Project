package com.authservice.microservice.entity;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Role {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer roleId;
	
	@Column(unique = true)
	String  role;
	
	@ManyToMany(mappedBy = "roles")
	@JsonIgnore
	Set<Customer> customers=new HashSet<>();

	
	
}
