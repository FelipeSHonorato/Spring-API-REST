package com.felipeshonorato.spring.forum.model.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;


@ActiveProfiles("test")
@RunWith(SpringRunner.class) //Anotação para ele carregar todo o sistema para efetuar um teste real
@AutoConfigureMockMvc //Anotação para ativar o MockMVC na classe de teste
@SpringBootTest
public class AutenticacaoControllerTest {

    @Autowired
    private MockMvc mockMvc; //Classe que simula um sistema MVC

    @Test
    public void devolve400CasoDadosAutenticacaoIncorretos() throws Exception {
        URI uri = new URI("/auth");
        String json = "{\"email\":\"invalido@email.com\", \"senha\": \"123456\"}\"";

        mockMvc
                .perform(MockMvcRequestBuilders //MVC faça uma requisição
                        .post(uri) //Do post para URI
                        .content(json) //Com conteúdo especificado no atributo json
                        .contentType(MediaType.APPLICATION_JSON)) //Com tipo de cabeçalho em Json
                .andExpect(MockMvcResultMatchers.status().is(400)); //Informando oq esperamos como resposta
    }
}