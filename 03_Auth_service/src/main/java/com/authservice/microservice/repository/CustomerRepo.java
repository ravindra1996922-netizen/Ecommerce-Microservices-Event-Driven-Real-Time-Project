package com.authservice.microservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.authservice.microservice.entity.Customer;
import com.authservice.microservice.entity.Role;


public interface CustomerRepo extends JpaRepository<Customer, Integer>  {
	
	Customer findByEmail(String email);
	
	Boolean existsByEmail(String email);
	
	

}
