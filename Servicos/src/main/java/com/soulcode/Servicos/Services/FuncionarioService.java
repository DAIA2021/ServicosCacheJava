package com.soulcode.Servicos.Services;

import com.soulcode.Servicos.Models.Cargo;
import com.soulcode.Servicos.Models.Funcionario;
import com.soulcode.Servicos.Repositories.CargoRepository;
import com.soulcode.Servicos.Repositories.FuncionarioRepository;
import com.soulcode.Servicos.Services.Exceptions.DataIntegrityViolationException;
import com.soulcode.Servicos.Services.Exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// 1 models, 2 Repository(interface), 3 Service, 4 controller
// quando se fala em serviços estamos falando dos metodos do crud da tabela
@Service // aqui camada de serviço, responsavel pela aplicação dos serviços(crud)

public class FuncionarioService {

    @Autowired //responsavel pela aplicação dos metodos  funcionario repository e JPA
    FuncionarioRepository funcionarioRepository;
    /// injeção de dependencia utilizada Autored metodos do funcionárioRepository JPA extends

    // primeiro serviço na tabela de funcionários vai ser a leitura de todos
    // os funcionários cadastrados
    // findAll  um metodo do spring do Data JPA ele busca todos os registros
    // de uma tabela

    /// importar List java util
    /// aqui foi criado o metodo , vai retornar

    @Autowired
    CargoRepository cargoRepository ;

    ///foi inserido @Autowired para puxar pelo idCargo e inserido get e set idCargo no funcionarioModels
    @Cacheable (value = "funcionariosCache")
    public List<Funcionario> mostrarTodosFuncionarios() {
        return funcionarioRepository.findAll();

    }

    // vamos criar mais um serviço relacionado ao funcionario
    /// criar um serviço de buscar apenas um funcionario
    /// pelo seu id

        @Cacheable(value = "funcionariosCache" , key = "idFuncionario")
        public Funcionario mostrarUmFuncionarioPeloId(Integer idFuncionario){
            Optional<Funcionario> funcionario =funcionarioRepository.findById(idFuncionario);
            return funcionario.orElseThrow(
                    () -> new EntityNotFoundException("Funcionário não cadastrado: " + idFuncionario)
            );

            //ou se não lança uma execção
            ///checadas que já existem criado
            // 1- foi criado pasta  Exceptions em Services  com o nome da classe  EntityNou... e criado
            ///2- no Controller tbem criar pacote com a classe StandarError
            ///3-no Controller criar classe no pacote exceptions a classe ResourceException....
            ///não checada vamos criar um execeção
            //optional se der erro notfoud,o optional faz com que rode, evitar travamento qdo der exceptional,no
            // caso qdo o funcionário não existir.
            //depois criar ir no controller para mapear rota
        }


        //vamos criar mais um serviço para buscar pelo seu email
        @Cacheable (value = "funcionariosCache")
        public Funcionario mostrarUmFuncionarioPeloEmail(String email) {
            Optional<Funcionario> funcionario = funcionarioRepository.findByEmail(email);
            return funcionario.orElseThrow();

        }

        ////hoje
        @Cacheable(value = "funcionariosCache" , key = "idCargo")
    public List<Funcionario> mostrarTodosFuncionariosDeUmCargo(Integer idCargo){
        Optional<Cargo> cargo = cargoRepository.findById(idCargo);
        return funcionarioRepository.findByCargo(cargo);
    }


            ///criação de serviço p cadastar um novo funcionário  aqui está faltando informação falta cat
    @CachePut(value = "funcionariosCache", key = "#funcionario.idFuncionario")
            public Funcionario cadastrarFuncionario(Funcionario funcionario, Integer idCargo){
                try {
                    Cargo cargo = cargoRepository.findById(idCargo).get();
                    funcionario.setCargo(cargo);
                    return funcionarioRepository.save(funcionario);
                }catch (Exception e){
                    throw new DataIntegrityViolationException("Erro ao cadastrar funcionário");
                }

            }


     @CacheEvict(value= "funcionariosCache", key = "#idFuncionário", allEntries = true)
    public void exluirFuncionario(Integer idFuncionario){
        funcionarioRepository.deleteById(idFuncionario);

    }
    @CachePut(value = "funcionariosCache", key = "#funcionario.idFuncionario")
    public Funcionario editarFuncionario(Funcionario funcionario){
        return funcionarioRepository.save(funcionario);
    }



    @CachePut(value = "funcionariosCache")
    public Funcionario salvarFoto(Integer idFuncionario, String caminhoFoto){
        Funcionario funcionario  = mostrarUmFuncionarioPeloId(idFuncionario);
        funcionario.setFoto(caminhoFoto);
        return funcionarioRepository.save(funcionario);
    }




}









