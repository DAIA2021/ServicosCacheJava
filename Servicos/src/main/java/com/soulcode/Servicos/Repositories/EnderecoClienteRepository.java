package com.soulcode.Servicos.Repositories;


import com.soulcode.Servicos.Models.EnderecoCliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoClienteRepository extends JpaRepository<EnderecoCliente, Integer> {
    // foi feito relacionamento na tabela cleinte oneto One
}
