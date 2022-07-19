package com.soulcode.Servicos.Controllers.Exceptios;

import com.soulcode.Servicos.Services.Exceptions.DataIntegrityViolationException;
import com.soulcode.Servicos.Services.Exceptions.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler {
    ///momento de instanciar o objeto da standaE


    @ExceptionHandler (EntityNotFoundException.class) ///anotação obrigatória para funcionar
    public ResponseEntity<StandardError> entityNotFound(EntityNotFoundException e, HttpServletRequest request){
        StandardError erro = new StandardError();
        erro.setTimestamp(Instant.now());
        erro.setStatus(HttpStatus.NOT_FOUND.value());
        erro.setError("Registro não encontrado");
        erro.setMessage(e.getMessage());   /// objeto e = EntityNFException
        erro.setPath(request.getRequestURI());
        erro.setTrace("EntityNoutFoundException");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);

    }

    //// 2 EXECESSÃO CHAMAR NO FUNCIONARIO SERVICE

    public ResponseEntity<StandardError> dataIntegrityViolationException (DataIntegrityViolationException e,HttpServletRequest request){
        StandardError erro = new StandardError();
        erro.setTimestamp(Instant.now());
        erro.setStatus(HttpStatus.CONFLICT.value());
        erro.setError("Atributo não encontrado");
        erro.setMessage(e.getMessage());   /// objeto e = EntityNFException
        erro.setPath(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);

    }

}
