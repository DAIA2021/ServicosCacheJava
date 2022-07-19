package com.soulcode.Servicos.Repositories;

import com.soulcode.Servicos.Models.Cargo;
import com.soulcode.Servicos.Models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/// de qual tabela e que tipo da chave primaria
public interface CargoRepository extends JpaRepository <Cargo, Integer> {

}
