package com.felipeshonorato.spring.forum.model.config.validacao;

import com.felipeshonorato.spring.forum.model.config.validacao.ErroDeFormularioDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

//Criada essa classe para personalizar os erros de validações ocorridas no programa.

@RestControllerAdvice
public class ErroDeValidacaoHandler {

    @Autowired
    private MessageSource messageSource;

    //@ExceptionHandler anotação para chamar uma exceção quando ocorrer algum erro dentro de um controller
    //@MethodArgu... anotação informa que a exceção que deve ser chamada é para erros de validações de formulário
    @ResponseStatus(code = HttpStatus.BAD_REQUEST) // Essa anotação informa ao usuário que teve um erro, caso não seja inserido isso será enviado um erro 200,como se tivesse sido tratado o erro
    @ExceptionHandler(MethodArgumentNotValidException.class)
    //Será devolvida uma lista com cada erro que ocorreu, essa lista foi criada em outra classe chamado ErroDeFomulario
    public List<ErroDeFormularioDto> handle(MethodArgumentNotValidException exception){

        List<ErroDeFormularioDto> dto = new ArrayList<>();
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        fieldErrors.forEach(e ->{
            String mensagem = messageSource.getMessage(e, LocaleContextHolder.getLocale());
            ErroDeFormularioDto erro = new ErroDeFormularioDto(e.getField(), mensagem);
            dto.add(erro);
        });
        return dto;
    }
}
