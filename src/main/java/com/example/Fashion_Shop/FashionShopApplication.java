package com.example.Fashion_Shop;

import com.example.Fashion_Shop.configuration.OpenAIConfig;
import com.example.Fashion_Shop.configuration.QdrantConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({OpenAIConfig.class, QdrantConfig.class})
public class FashionShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(FashionShopApplication.class, args);
	}

}
