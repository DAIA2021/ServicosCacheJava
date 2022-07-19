package com.soulcode.Servicos.Models;

public enum StatusPagamento {

    /// colocar lancado e quitado

    LANCADO   ("Lancado"),
    QUITADO   ("Quitado");



    private  String conteudo; ///colocar esta informação / criar construtor

    StatusPagamento(String conteudo) {
        this.conteudo = conteudo;
    }

    public String getConteudo() {
        return conteudo;
    }


}
