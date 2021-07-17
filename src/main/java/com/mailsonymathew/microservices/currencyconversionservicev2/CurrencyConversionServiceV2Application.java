package com.mailsonymathew.microservices.currencyconversionservicev2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
//@EnableFeignClients("com.mailsonymathew.microservices.currencyconversionservicev2")
@EnableFeignClients
@EnableDiscoveryClient
@OpenAPIDefinition(info =@Info(title = "Currency Coversion API", version = "1.0", description = "Documentation of Currency Coversion API v1.0"))
public class CurrencyConversionServiceV2Application {

	public static void main(String[] args) {
		SpringApplication.run(CurrencyConversionServiceV2Application.class, args);
	}

}
