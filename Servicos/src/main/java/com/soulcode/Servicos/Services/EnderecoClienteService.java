package com.soulcode.Servicos.Services;

import com.soulcode.Servicos.Models.Cliente;
import com.soulcode.Servicos.Models.EnderecoCliente;
import com.soulcode.Servicos.Repositories.ClienteRepository;
import com.soulcode.Servicos.Repositories.EnderecoClienteRepository;
import org.hibernate.annotations.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service //BIN

public class EnderecoClienteService {

    @Autowired //INJEÇÃO  DE DEPENDENCIA DO JPA
    EnderecoClienteRepository enderecoClienteRepository;

    @Autowired /// aqui foi necessário injeção  pelo método 3 regra de negocio
    ClienteRepository clienteRepository;
    @Cacheable("enderecosCache")
    public List<EnderecoCliente> mostrarTodosEnderecosCliente() {
        return enderecoClienteRepository.findAll();

    }
     @Cacheable(value = "enderecosCache", key = "#idEnderecoCliente")
    public EnderecoCliente mostrarUmEnderecoPeloId(Integer idEnderecoCliente) {
        Optional<EnderecoCliente> enderecoCliente = enderecoClienteRepository.findById(idEnderecoCliente);
        return enderecoCliente.orElseThrow();
    }


    ////CADASTRO DE UM NOVO ENDEREÇO REGRAS
    /// regaras para cadastrar um endereço  o cliente já deve estar cadastrado no database
    // no momento do caf=dastro do endereço , precisamos passar o id do cliente dono desses endereço
    // o id do endereço vai ser o mesmo id do cliente
    ///não permitir que um enderço seja salvo sem a existência do respectivo cliente

    /// idCliente para qual queremos cadastrar o endereço

    @CachePut(value = "enderecosCache", key = "#idCliente")
    public EnderecoCliente cadastrarEnderecoDoCliente(EnderecoCliente enderecoCliente, Integer idCliente) throws Exception {
        //estamos declarando um optional de client e atribuindo para este os dados do cliente que receberá o novo endereço
        Optional<Cliente> cliente = clienteRepository.findById(idCliente);
        if (cliente.isPresent()) {
            enderecoCliente.setIdEndereco(idCliente); //3
            enderecoClienteRepository.save(enderecoCliente);

            cliente.get().setEnderecoCliente(enderecoCliente); //colocar informação set
            clienteRepository.save(cliente.get());
            return enderecoCliente;
        }else{
            throw new Exception();
        }

    }

    @CachePut(value = "enderecosCache", key = "#cliente.idCliente")
    public EnderecoCliente editarEndereco(EnderecoCliente enderecoCliente){
        return enderecoClienteRepository.save(enderecoCliente);
    }

}
