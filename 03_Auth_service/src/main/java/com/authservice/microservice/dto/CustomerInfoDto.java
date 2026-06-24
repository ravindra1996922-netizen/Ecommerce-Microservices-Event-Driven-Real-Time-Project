package com.authservice.microservice.dto;

import java.util.HashSet;
import java.util.Set;

import com.authservice.microservice.entity.Role;

import lombok.Builder;
import lombok.Data;
@Builder
@Data
public class CustomerInfoDto {

	String name;
	String email;
	Set<Role> roles = new HashSet<>();
}
