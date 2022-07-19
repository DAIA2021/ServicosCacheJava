package com.soulcode.Servicos.Models;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cargo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCargo ;

    @Column(nullable = false, length = 100) //não pode ser nulo
    private String nome;

    @Column(nullable = false, length = 100)
    private String descricao;

    @Column (nullable = false)
    private double salario;

    // ligação ente tabela cargo e funcionário de um pela chave estrangeira, criado get e setters
    /// jsonignore um para vários cargo private Funcionario ligação para tabela Funcionario
    /// na tela funcionario também foi inserido informação
    ///@OneToOne
    //    @JoinColumn(name = "idCargo") /// coluna id cargo
    //    private Cargo cargo;
    //unidirecional relacão com funcionario a chave de ligação foi na tabela de funcionario



    public Integer getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(Integer idCargo) {
        this.idCargo = idCargo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }



}
