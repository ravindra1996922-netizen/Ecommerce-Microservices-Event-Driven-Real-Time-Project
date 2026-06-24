package com.authservice.microservice.dto;

import java.util.HashSet;
import java.util.Set;

import com.authservice.microservice.entity.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerRegisterInfoDto {

	@NotBlank(message = "customer full name required")
	private String fullName;
	
	@Email
	@NotBlank
	private String email;
	@NotBlank
	private String pwd;
	
	private Set<Role> roles= new HashSet<>();
}
