package com.authservice.microservice.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.authservice.microservice.dto.CustomerRegisterInfoDto;
import com.authservice.microservice.entity.Customer;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomerMapper {
	
	 final ModelMapper modelMapper;
	
	public Customer convertIntoEntity(CustomerRegisterInfoDto customerRegisterInfoDto) {
		
		return modelMapper.map(customerRegisterInfoDto, Customer.class);
	}
	
	

}
