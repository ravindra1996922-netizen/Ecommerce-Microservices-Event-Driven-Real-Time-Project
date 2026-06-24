package com.authservice.microservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.authservice.microservice.entity.Role;

public interface RoleRepo  extends JpaRepository<Role, Integer>{

	Boolean existsByRole(String role);
	 
	

	 
	
	 
}
