package com.apigateway.microservice.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class RouterConfig {

    @Bean
    public RouteLocator router(RouteLocatorBuilder locatorBuilder) {

        log.info("router running");
        return locatorBuilder.routes()

                .route("AUTH-SERVICE", req -> req
                        .path("/auth/**")
                        .uri("lb://AUTH-SERVICE"))

                .route("PRODUCT-SERVICE", req -> req
                        .path("/product/**",
                              "/addProduct",
                              "/getproductbyId/**",
                              "/getAllProducts",
                              "/search",
                              "/updateProduct",
                              "/deleteProduct",
                              "/getProductByCategory")
                        .uri("lb://PRODUCT-SERVICE"))

                .build();
    }
}