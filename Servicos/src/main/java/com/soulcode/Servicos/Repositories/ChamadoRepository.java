package com.soulcode.Servicos.Repositories;

import com.soulcode.Servicos.Models.Chamado;
import com.soulcode.Servicos.Models.Cliente;
import com.soulcode.Servicos.Models.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ChamadoRepository extends JpaRepository<Chamado,Integer> {

    List<Chamado> findByCliente(Optional<Cliente> cliente);

    List<Chamado> findByFuncionario(Optional<Funcionario> funcionario); // pesquisar pelo id atributo

    //status do serviço foi criado manualmente este metodo, foi no mysql

    @Query(value = "SELECT * FROM chamado WHERE status =:status ", nativeQuery = true) //status sem espaço
    List<Chamado> finByStatus(String status);

/// metodo adicionado manualmente
    @Query(value="SELECT * FROM chamado WHERE data_entrada BETWEEN :data1 AND :data2", nativeQuery = true)
    List<Chamado> findByIntervaloData(Date data1, Date data2);



}

/// controle de ordem de serviço funcionarios vamos ter vários tipos de chamados
/// todos serviços/ chamados  de determinado cliente
// vamos criar vários tipos de chamados
