package com.felipeshonorato.spring.forum.model.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private AutenticacaoService autenticacaoService;

    //Necessário sobrescrever esse método da classe WebSecurity... para poder liberar a injeção do AutenticacaoService
    @Override
    @Bean //Anotação para exportar a classe para o Spring poder fazer a injeção de dependencia
    protected AuthenticationManager authenticationManager() throws Exception{
        return super.authenticationManager();
    }

    //Método que efetua a configurações de autenticação (login..)
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(autenticacaoService).passwordEncoder(new BCryptPasswordEncoder()); //Informa qual é a classe que contem a lógica de autenticação para login
    }

    //Método que efetua a configurações de autorização (permissões de acesso de url..)
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET,"/topicos").permitAll() //Libera acesso via Get para todos da url topicos
                .antMatchers(HttpMethod.GET, "/topicos/*").permitAll() //Libera acesso via Get para todos da url topicos mais outra coisa
                .antMatchers(HttpMethod.POST, "/auth").permitAll()
                .anyRequest().authenticated() //Informa que todos as outras urls necessitam de autenticação
                .and().csrf().disable() //CSRF => cross-site request forgery, é um tipo de ataque hacker que acontece nas aplicações web
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); //Informa que a autenticação será via stateless
    }

    //Método que efetua a configurações de recursos estáticos (js, css, imagens...)
    @Override
    public void configure(WebSecurity web) throws Exception {
    }

}
