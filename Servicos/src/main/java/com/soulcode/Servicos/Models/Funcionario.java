package com.soulcode.Servicos.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//models ´é o modelo da classe,
// 1 models, 2 Repository(interface), 3 Service, 4 controller
// depois de colocar as informações criar,pacote repositories clienteRepository a Interface
@Entity     /// ta representando uma entidade(tabela) tem que importar aqui
public class Funcionario {

    @Id ///chave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) //identifica que ele autoincremente gerado automatico
    private Integer idFuncionario; // chave unica

    @Column(nullable = false, length = 100) //coluna da tabela, não pode ser nulo
    private String nome;

    @Column(nullable = false, length = 100,unique = true) // email unico
    private String email;

    @Column(nullable = true ) // no momento do cadastro , a foto não é obrigatorio
    private String foto ; /// está como  String que vai guardado o caminho


    // ligação da tabela chamado com o funcionario criado get e set
    @JsonIgnore
    @OneToMany(mappedBy = "funcionario") ///um fncionario para vários chamados
    private List<Chamado> chamados = new ArrayList<Chamado>();


    // ligação entre tabela cargo com funcionario adicionado uma coluna na tabela funcionario
    // funcionarios vários em um função programdor + 1 em uma empresa
    @ManyToOne ///(Many)muitos a tabela que estamos inserindo o relacimto (one) funcionario  para um cargo
    @JoinColumn(name = "idCargo") /// juntar coluna id cargo criado na tabela do funcionario
    private Cargo cargo;




    //// get e seters

    public Integer getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(Integer idFuncionario) {
        this.idFuncionario = idFuncionario;
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

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public List<Chamado> getChamados() {
        return chamados;
    }

    public void setChamados(List<Chamado> chamados) {
        this.chamados = chamados;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }
}
