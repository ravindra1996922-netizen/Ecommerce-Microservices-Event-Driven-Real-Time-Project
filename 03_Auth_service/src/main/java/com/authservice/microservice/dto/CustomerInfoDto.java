package com.authservice.microservice.dto;

import java.util.HashSet;
import java.util.Set;

import com.authservice.microservice.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerInfoDto {

	String name;
	String email;
	Set<Role> roles = new HashSet<>();
			
}
