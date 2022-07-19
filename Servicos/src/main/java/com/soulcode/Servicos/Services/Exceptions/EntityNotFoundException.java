package com.soulcode.Servicos.Services.Exceptions;

public class EntityNotFoundException extends RuntimeException{

    ///construtor e enviar para SUPERCLASSE lançar Funcionarioservice
    public EntityNotFoundException(String msg){
        super(msg);
    }
}
