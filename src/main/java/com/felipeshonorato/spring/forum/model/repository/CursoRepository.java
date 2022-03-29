package com.felipeshonorato.spring.forum.model.repository;

import com.felipeshonorato.spring.forum.model.modelo.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Long> {

    Curso findByNome(String nomeCurso);

}
