package com.desafio.buscacep;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class BuscaCepApplication {
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
		
	public static void main(String[] args) {
		SpringApplication.run(BuscaCepApplication.class, args);
	}

}
