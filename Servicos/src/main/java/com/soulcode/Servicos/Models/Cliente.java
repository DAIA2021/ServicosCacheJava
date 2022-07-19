package com.soulcode.Servicos.Models;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cliente {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY )  //Chave primaria
    private Integer idCliente;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, length = 100,unique = true)
    private String email;

    /// LIGAÇÃO TABELA CHAMADOS COM CLIENTE criado get e set

    @JsonIgnore // qundo bidericonal
    @OneToMany(mappedBy = "cliente")
    private List<Chamado> chamados = new ArrayList<>();


    //// relacionamento com a tabela endereço cliente GERAR GET E SET
    @OneToOne(cascade = CascadeType.ALL) //efeito cascata
    @JoinColumn(name = "id_endereço", unique = true)
    private EnderecoCliente enderecoCliente;


//// get e seters
    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Chamado> getChamados() {
        return chamados;
    }

    public void setChamados(List<Chamado> chamados) {
        this.chamados = chamados;
    }

    public EnderecoCliente getEnderecoCliente() {
        return enderecoCliente;
    }

    public void setEnderecoCliente(EnderecoCliente enderecoCliente) {
        this.enderecoCliente = enderecoCliente;
    }
}
