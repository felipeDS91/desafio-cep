package com.desafio.buscacep.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Bean
	public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()                                   
                .apis(RequestHandlerSelectors.basePackage("com.desafio.buscacep.api.controller"))
                .paths(PathSelectors.any())
                .build()      
                .useDefaultResponseMessages(false)
                .apiInfo(metaInfo());
    }

    private ApiInfo metaInfo() {

        return new ApiInfo(
        		"Consulta CEP API REST",
                "API para consulta de pontos cep",
                "1.0",
                "",
                new Contact("Felipe Douglas", "https://github.com/felipeDS91", "felipe.ds@outlook.com"),
                "",
                "", 
                Collections.emptyList());

    }
	

}
