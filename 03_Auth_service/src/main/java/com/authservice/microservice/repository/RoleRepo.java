package com.authservice.microservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.authservice.microservice.entity.Role;

public interface RoleRepo  extends JpaRepository<Role, Integer>{

	Boolean existsByRole(String role);
	
	
	@Query(value = "select * from role where role=?" ,nativeQuery = true)
	public Role getRole(String role);
	 
	

	 
	
	 
}
