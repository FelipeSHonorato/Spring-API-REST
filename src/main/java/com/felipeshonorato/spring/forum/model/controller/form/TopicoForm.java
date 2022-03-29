package com.felipeshonorato.spring.forum.model.controller.form;

import com.felipeshonorato.spring.forum.model.modelo.Curso;
import com.felipeshonorato.spring.forum.model.modelo.Topico;
import com.felipeshonorato.spring.forum.model.repository.CursoRepository;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class TopicoForm {

    //Anotações abaixo são utilizadas para criar validações para campos atravez do validation
    @NotNull @NotEmpty
    private String titulo;
    @NotNull @NotEmpty
    private String mensagem;
    @NotNull @NotEmpty
    private String nomeCurso;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getNomeCurso() {
        return nomeCurso;
    }

    public void setNomeCurso(String nomeCurso) {
        this.nomeCurso = nomeCurso;
    }

    public Topico converter(CursoRepository cursoRepository) {
        Curso curso = cursoRepository.findByNome(nomeCurso);
        return new Topico(titulo, mensagem, curso);
    }
}
