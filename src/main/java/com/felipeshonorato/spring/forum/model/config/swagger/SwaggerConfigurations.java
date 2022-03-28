package com.felipeshonorato.spring.forum.model.config.swagger;

import com.felipeshonorato.spring.forum.model.modelo.Usuario;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;

//Classe criada para configurar a utilização do Swagger

@Configuration
public class SwaggerConfigurations {

    @Bean
    public Docket forumApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.felipeshonorato.spring.forum.model"))
                .paths(PathSelectors.ant("/**")) //Indica os endereços que deverão ser gerados a documentação
                .build()
                .ignoredParameterTypes(Usuario.class) //Ignora a classe Usuário por questões de senhas

                //Insere um campo nas ações da Documentação para inserir o token de autorização -> "Bearer + token"
                .globalOperationParameters(Arrays.asList(
                        new ParameterBuilder()
                                .name("Authorization")
                                .description("Insert Bearer + Token JWT")
                                .modelRef(new ModelRef("string"))
                                .parameterType("header")
                                .required(false)
                                .build()));
    }
}
