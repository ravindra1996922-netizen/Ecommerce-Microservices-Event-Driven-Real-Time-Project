package com.authservice.microservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.authservice.microservice.entity.Customer;
import com.authservice.microservice.entity.RefreshToken;

import jakarta.transaction.Transactional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer>{

	
	 Optional<RefreshToken> findByToken(String token);

	   
	    Optional<RefreshToken> findByCustomer(Customer customer);

	    
	    @Modifying
	    @Transactional
	    @Query("DELETE FROM RefreshToken rt WHERE rt.customer = :customer")
	    void deleteByCustomer(Customer customer);

	 
	    boolean existsByCustomer(Customer customer);
}
