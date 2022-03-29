package com.felipeshonorato.spring.forum.model.config.security;

import com.felipeshonorato.spring.forum.model.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@Profile("prod") //Anotação para informar ao Spring que essa classe tem que ser carregada somente durante o processo de produção
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenService tokenService;

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
                //.anyRequest().permitAll()
                .antMatchers(HttpMethod.GET,"/topicos").permitAll() //Libera acesso via Get para todos da url topicos
                .antMatchers(HttpMethod.GET, "/topicos/*").permitAll() //Libera acesso via Get para todos da url topicos mais outra coisa
                .antMatchers(HttpMethod.POST, "/auth").permitAll()
                .antMatchers(HttpMethod.GET, "/actuator/**").permitAll() //Libera acesso ao monitoramento da API
                .antMatchers(HttpMethod.DELETE, "/topicos/*").hasRole("MODERADOR")//Libera a opção de delete somente para quem é moderador
                .anyRequest().authenticated() //Informa que todos as outras urls necessitam de autenticação
                .and().csrf().disable() //CSRF => cross-site request forgery, é um tipo de ataque hacker que acontece nas aplicações web
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //Informa que a autenticação será via stateless
                .and().addFilterBefore(new AutenticacaoViaTokenFilter(tokenService, usuarioRepository), UsernamePasswordAuthenticationFilter.class); //Solicita ao Spring que faça uma autenticação antes com o token do usuário
    }

    //Método que efetua a configurações de recursos estáticos (js, css, imagens...)
    //Esse método através da Web.ignoring irá liberar o acesso do swagger para criar documentação da API
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/**.html", "/v2/api-docs", "/webjars/**", "/configuration/**", "/swagger-resources/**");
    }

}
