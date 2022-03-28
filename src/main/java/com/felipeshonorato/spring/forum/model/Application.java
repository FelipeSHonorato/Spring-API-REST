package com.felipeshonorato.spring.forum.model;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSpringDataWebSupport //Anotaçao para habilitar a paginação no Spring
@EnableCaching //Anotação para habilitar o cache na aplicação
@EnableSwagger2 //Anotação para habilitar o Swagger para de geração de Doc da API
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
