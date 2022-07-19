package com.soulcode.Servicos.Repositories;

import com.soulcode.Servicos.Models.Cargo;
import com.soulcode.Servicos.Models.Chamado;
import com.soulcode.Servicos.Models.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FuncionarioRepository extends JpaRepository <Funcionario ,Integer> { // classe e chave primaria
    Optional<Funcionario> findByEmail(String email); // só assinatura do metodo
    //findBy procurar por email metodo vai buscar seu funcionario pelo email, pode colocar nome, foto
    //2 itens de uma vez Optional<Funcionario> findByNameAndEmail ( String nome, String email);

    List<Funcionario> findByCargo(Optional<Cargo> cargo);

}

// 2 Interface,ela vai ser subclasse da  JpaRepository SUPERCLASSE ja tem os metodos que vamos utilizar
// funcionario, intereger a chave primaria
/// assinatura dos metodos
//Optional <Funcionario> findByNome procurar pelo atributo
// Optional<Funcionario> findByNomeAndEmail(String nome,String email);
// JPA já tem os metodos, onde consta o CONTRATO
//criar service , cadastar, listar
