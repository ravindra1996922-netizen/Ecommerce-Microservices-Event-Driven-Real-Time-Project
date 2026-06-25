package com.authservice.microservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/product")
@Slf4j
public class Controller {
	
	
	
	@GetMapping("/get")
	public String getMsg() {
		
		log.info("product controller running::::::  ");
		return " mes recived";
		}
	
	@PostMapping("/post")
	public String postMap() {
		return "comming from post mapping  my role is store owner";
	}

}
