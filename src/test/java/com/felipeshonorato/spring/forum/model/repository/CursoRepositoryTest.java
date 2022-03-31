package com.felipeshonorato.spring.forum.model.repository;

import com.felipeshonorato.spring.forum.model.modelo.Curso;
import org.junit.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/** Como padrão do Spring, a execução de um teste ele utiliza banco de dados em memória, no caso H2, caso
 *  quisermos utilizar um banco de dados externo devemos colocar a seguinte anotação acima do nome da classe:**/

 //@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)

 /** O correto é criarmos um application.properties exclusivo para o ambiente de test e adicionamos uma nova anotação para
 * indicar onde o Spring deve carregar.**/

@ActiveProfiles("test")

@RunWith(SpringRunner.class) //Anotação para ele carregar todo o sistema para efetuar um teste real
@DataJpaTest //Anotação para informar que essa classe de teste tb utiliza o Spring além do JUnity
public class CursoRepositoryTest {

    @Autowired
    private CursoRepository repository;

    @Test
    public void carregaNomeCursoDigitado() {
        String nomeCurso = "HTML 5";
        Curso curso = repository.findByNome(nomeCurso);
        Assert.assertNotNull(curso);
        Assert.assertEquals(nomeCurso, curso.getNome());
    }

    @Test
    public void erroAoCarregarNomeCursoDigitado(){
        String nomeCurso = "JPA";
        Curso curso = repository.findByNome(nomeCurso);
        Assert.assertNull(curso);
    }
}