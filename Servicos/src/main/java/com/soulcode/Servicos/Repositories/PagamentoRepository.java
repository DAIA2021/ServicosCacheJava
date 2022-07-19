package com.soulcode.Servicos.Repositories;

import com.soulcode.Servicos.Models.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PagamentoRepository extends JpaRepository <Pagamento, Integer> {

      ///primeiro status coluna da tabela
      @Query(value = "SELECT * FROM pagamento WHERE status = :status", nativeQuery = true)
      List<Pagamento> findByStatus(String status); //:variavel q vai ser subst na tabela

      @Query(value = "SELECT pagamento.*, chamado.id_chamado, chamado.titulo, cliente.id_cliente, cliente.nome \n" +
              "FROM chamado RIGHT JOIN pagamento ON chamado.id_chamado = pagamento.id_pagamento  \n" +
              "LEFT JOIN cliente ON cliente.id_cliente = chamado.id_cliente;\n",nativeQuery = true)
      List<List> orcamentoCOmServicoCliente();
}



