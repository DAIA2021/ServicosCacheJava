package com.soulcode.Servicos.Models;
/// classe apenas um atributo conteudo
public enum StatusChamado {

   RECEBIDO("Recebido"),
    ATRIBUIDO("Atribuido"),
    CONCLUIDO("Concluido"),
    ARQUIVADO("Arquivado");

   private String conteudo; //atributo abaixo o construtor e criado o get somente

    StatusChamado(String conteudo){
        this.conteudo = conteudo;
    }

    public String getConteudo() {
        return conteudo;
    }


}

/// pode ter 4 status o serviço
