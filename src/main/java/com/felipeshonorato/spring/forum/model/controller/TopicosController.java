package com.felipeshonorato.spring.forum.model.controller;

import com.felipeshonorato.spring.forum.model.controller.form.AtualizacaoTopicoForm;
import com.felipeshonorato.spring.forum.model.controller.form.TopicoForm;
import com.felipeshonorato.spring.forum.model.dto.DetalhesDoTopicoDto;
import com.felipeshonorato.spring.forum.model.dto.TopicoDto;
import com.felipeshonorato.spring.forum.model.modelo.Topico;
import com.felipeshonorato.spring.forum.model.repository.CursoRepository;
import com.felipeshonorato.spring.forum.model.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController //Anotação para informar que essa classe é um controller do tipo rest onde não será direcionada para uma página tradicional de view.
@RequestMapping("/topicos") //Informa ao spring que todo o conteúdo dessa controller será direcionado a página de url topicos
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

    //@Validation para avisar o Spring que existem campos com validações a serem feitas.
    //@RequestBody informa ao Spring que os dados irão ser enviado através do corpo e não da barra de navegação como acontece em GET
    @PostMapping //Informa que dentro de /topicos esse método será utilizando o POST
    @Transactional //Quando o método for efetuar alguma modificação no BD deve-se utilizar essa anotação (PUT, DELETE ou POST)
    public ResponseEntity<TopicoDto> cadastrar(@RequestBody @Valid TopicoForm topicoForm, UriComponentsBuilder uriComponentsBuilder){
        Topico topico = topicoForm.converter(cursoRepository);
        topicoRepository.save(topico);

        //O processo de se implantar uma respota URI é uma boa prática para o REST pois devolve que a ação realmente foi efetuada com sucesso e não o código 201 somente.
        URI uri = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new TopicoDto(topico));
    }

    //@PathVariable diz para o Spring que o id do GetMapping será recebido através da URL de requisição
    @GetMapping ("/{id}")
    public ResponseEntity<DetalhesDoTopicoDto> detalhar(@PathVariable("id") Long codigo){

        Optional<Topico> topico = topicoRepository.findById(codigo);
        if(topico.isPresent()){
            return ResponseEntity.ok(new DetalhesDoTopicoDto(topico.get()));}
        else{
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<TopicoDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoTopicoForm topicoForm){

        Optional<Topico> optional = topicoRepository.findById(id);

        if(optional.isPresent()){
            Topico topico = topicoForm.atualizar(id, topicoRepository);
            return ResponseEntity.ok(new TopicoDto(topico));
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> remover(@PathVariable Long id){

        Optional<Topico> optional = topicoRepository.findById(id);
        if(optional.isPresent()){
            topicoRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
