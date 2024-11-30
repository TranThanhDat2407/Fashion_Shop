package com.example.Fashion_Shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class FashionShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(FashionShopApplication.class, args);
	}

}
