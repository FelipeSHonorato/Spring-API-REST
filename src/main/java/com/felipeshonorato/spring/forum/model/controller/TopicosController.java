package com.felipeshonorato.spring.forum.model.controller;

import com.felipeshonorato.spring.forum.model.controller.form.TopicoForm;
import com.felipeshonorato.spring.forum.model.dto.TopicoDto;
import com.felipeshonorato.spring.forum.model.modelo.Topico;
import com.felipeshonorato.spring.forum.model.repository.CursoRepository;
import com.felipeshonorato.spring.forum.model.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


import java.net.URI;
import java.util.List;

@RestController //Anotação para informar que essa classe é um controller do tipo rest onde não será direcionada para uma página tradicional de view.
@RequestMapping("/topicos") //Informa ao spring que todo o conteúdo dessa controller será direcionado a página de url /topicos
public class TopicosController {

    //Efetuando a injeção de dependencia
    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private CursoRepository cursoRepository;



   // @RequestMapping("/topicos") //Esssa anotação poderia ser utilizada por métodos também porem abre a possibilidade de ambiguidade com outro método que responde na msm url
   // @ResponseBody //Anotação para avisar ao Spring que a navegação não será para uma página tradicional de view
    @GetMapping //Informa que dentro de /topicos esse método será utilizando o GET
    public List<TopicoDto> lista(String nomeCurso){

        if (nomeCurso == null){
            List<Topico>topicos = topicoRepository.findAll();
            return TopicoDto.converter(topicos);
        }else{
            List<Topico>topicos = topicoRepository.findByCurso_Nome(nomeCurso);
            return TopicoDto.converter(topicos);
        }
    }

    //@RequestBody informa ao Spring que os dados irão ser enviado através do corpo e não da barra de navegação como acontece em GET
    @PostMapping //Informa que dentro de /topicos esse método será utilizando o POST
    public ResponseEntity<TopicoDto> cadastrar(@RequestBody TopicoForm topicoForm, UriComponentsBuilder uriComponentsBuilder){
        Topico topico = topicoForm.converter(cursoRepository);
        topicoRepository.save(topico);

        //O processo de se implantar uma respota URI é uma boa prática para o REST pois devolve que a ação realmente foi efetuada com sucesso e não o código 201 somente.
        URI uri = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new TopicoDto(topico));
    }
}
