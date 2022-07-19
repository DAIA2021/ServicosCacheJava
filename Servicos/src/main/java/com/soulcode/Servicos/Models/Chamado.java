package com.soulcode.Servicos.Models;

//  tabela de chamados vai ter relação com serviço e cliente

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Chamado {

    @Id
    private Integer idChamado; //int

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = true)
    private String descricao;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column(columnDefinition = "date", nullable = false)
    private Date dataEntrada;

    //StatusChamado do enum a classe criado com 4 atributos
    ///tipo personizado pelo enum para o atributo criar classe enum
    @Enumerated(EnumType.STRING)
    private StatusChamado status;

    @ManyToOne // muitos chamados para um funcionario
    @JoinColumn(name = "idFuncionario") //ligação entre as tabelas chamado e (funcionario)
    private Funcionario funcionario;

    @ManyToOne // vários/muitos chamados para um cliente
    @JoinColumn(name = "idCliente") //ligação 2 entre as tabelas (cliente)
    private Cliente cliente;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn (name = "idPagamento" , unique = true )  /// criar get e seters  da chave primaria
    private Pagamento pagamento;



    public Integer getIdChamado() {
        return idChamado;
    }

    public void setIdChamado(Integer idChamado) {
        this.idChamado = idChamado;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(Date dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public StatusChamado getStatus() {
        return status;
    }

    public void setStatus(StatusChamado status) {
        this.status = status;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }
}
