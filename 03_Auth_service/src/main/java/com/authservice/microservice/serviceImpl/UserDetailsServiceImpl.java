package com.authservice.microservice.serviceImpl;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.authservice.microservice.entity.Customer;
import com.authservice.microservice.repository.CustomerRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl  implements UserDetailsService{

	private final CustomerRepo customerRepo;
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Customer customer = customerRepo.findByEmail(email);
		
		 Set<SimpleGrantedAuthority> authorities = customer
	                .getRoles()
	                .stream()
	                .map(role -> new SimpleGrantedAuthority(
	                        "ROLE_" + role.getRole()
	                ))
	                .collect(Collectors.toSet());
		
		return new  User(customer.getEmail(),customer.getPwd(),authorities);
		
	
	}

}
