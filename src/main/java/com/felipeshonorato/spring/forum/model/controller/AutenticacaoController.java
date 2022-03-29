package com.felipeshonorato.spring.forum.model.controller;

import com.felipeshonorato.spring.forum.model.config.security.TokenService;
import com.felipeshonorato.spring.forum.model.controller.form.LoginForm;
import com.felipeshonorato.spring.forum.model.controller.dto.TokenDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@Profile(value = {"prod", "test"}) //Anotação para informar ao Spring que essa classe tem que ser carregada somente durante o processo de produção
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<TokenDto> autenticar(@RequestBody @Valid LoginForm form){

        UsernamePasswordAuthenticationToken dadosLogin = form.converter();

        //Usamos Try e Catch para caso o login seja válido token é gerado, caso contrário volta uma exception de bad request.
        try {
            Authentication authentication = authenticationManager.authenticate(dadosLogin);
            String token = tokenService.gerarToken(authentication);
            return ResponseEntity.ok(new TokenDto(token, "Bearer"));
        } catch (AuthenticationException e){
            return ResponseEntity.badRequest().build();
        }
    }
}
