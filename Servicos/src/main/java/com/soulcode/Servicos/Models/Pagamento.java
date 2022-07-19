package com.soulcode.Servicos.Models;

import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;

@Entity
public class Pagamento {

    @Id
    private Integer idPagamento;


    @NumberFormat(pattern = "#.##0,00")
    @Column (nullable = false )
    private double valor;

    @Column(nullable = false, length = 50)
    private String formaPagamento; // aui ta diferente da professora tá form



    @Enumerated(EnumType.STRING)  // colocar esta anotação do ENUM get e setters
    private StatusPagamento status;

    // CRIAR ENUM COM STATUS DE PAGAMENTO



    public Integer getIdPagamento() {
        return idPagamento;
    }


    public void setIdPagamento(Integer idPagamento) {
        this.idPagamento = idPagamento;
    }


    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public StatusPagamento getStatus() {
        return status;
    }

    public void setStatus(StatusPagamento status) {
        this.status = status;
    }
}
