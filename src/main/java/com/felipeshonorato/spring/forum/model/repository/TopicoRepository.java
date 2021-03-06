package com.felipeshonorato.spring.forum.model.repository;

import com.felipeshonorato.spring.forum.model.modelo.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicoRepository extends JpaRepository <Topico, Long>{

    //O spring consegue criar a query automaticamente, ele vai até a entidade Curso e procura pelo atributo nome.
    Page<Topico> findByCurso_Nome(String nomeCurso, Pageable paginacao);
}
