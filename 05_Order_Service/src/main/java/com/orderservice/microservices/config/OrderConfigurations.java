package com.orderservice.microservices.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderConfigurations {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
