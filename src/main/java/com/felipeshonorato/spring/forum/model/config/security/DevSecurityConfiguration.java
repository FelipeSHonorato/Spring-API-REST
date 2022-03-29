package com.felipeshonorato.spring.forum.model.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
@Profile("dev") //Anotação para informar ao Spring que essa classe tem que ser carregada somente durante o processo de desenvolvimento
public class DevSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //.anyRequest().permitAll()
                .antMatchers(HttpMethod.GET,"/topicos").permitAll()
                .and().csrf().disable();
    }

    //Método que efetua a configurações de recursos estáticos (js, css, imagens...)
    //Esse método através da Web.ignoring irá liberar o acesso do swagger para criar documentação da API
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/**.html", "/v2/api-docs", "/webjars/**", "/configuration/**", "/swagger-resources/**");
    }

}
