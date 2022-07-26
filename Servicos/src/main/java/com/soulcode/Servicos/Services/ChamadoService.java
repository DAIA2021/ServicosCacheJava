package com.soulcode.Servicos.Services;

import com.soulcode.Servicos.Models.Chamado;
import com.soulcode.Servicos.Models.Cliente;
import com.soulcode.Servicos.Models.Funcionario;
import com.soulcode.Servicos.Models.StatusChamado;
import com.soulcode.Servicos.Repositories.ChamadoRepository;
import com.soulcode.Servicos.Repositories.ClienteRepository;
import com.soulcode.Servicos.Repositories.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/// AQUI PARTE LÓGICA

@Service
public class ChamadoService {
    @Autowired
    ChamadoRepository chamadoRepository;

    @Autowired ///injeção de dependencia
    ClienteRepository clienteRepository;

    @Autowired
    FuncionarioRepository funcionarioRepository;
    @Cacheable("chamadosCache")
    public List<Chamado> mostrarTodosChamados() {
        return chamadoRepository.findAll();
    }

    @Cacheable(value = "chamadosCache", key = "#idchamado")
    public Chamado mostrarUmChamadoPeloId(Integer idChamado) {
        Optional<Chamado> chamado = chamadoRepository.findById(idChamado);
        return chamado.orElseThrow();
    }

    /// no jPA foi incluido este metodo list<chamado> foi inserido o Autoride do clienteRepository
    ///daqui ir no controller @GetMapping

    @Cacheable(value = "chamadosCache", key = "#idCliente")
    public List<Chamado> buscarChamadosPeloCliente(Integer idCliente) {
        Optional<Cliente> cliente = clienteRepository.findById(idCliente);
        return chamadoRepository.findByCliente(cliente);
    }


    @Cacheable(value = "chamadosCache", key = "#idFuncionario")
    public List<Chamado> buscarChamadosPeloFuncionario(Integer idFuncionario) {
        Optional<Funcionario> funcionario = funcionarioRepository.findById(idFuncionario);
        return chamadoRepository.findByFuncionario(funcionario);
    }
    @Cacheable ( value = "chamadosCache", key = "#status")
    public List<Chamado> buscarChamadoPeloStatus(String status) {
        return chamadoRepository.finByStatus(status);
    }


    public List<Chamado> buscarPorIntervaloData(Date data1, Date data2) {
        return chamadoRepository.findByIntervaloData(data1, data2);
    }

    /// cadastrar um novo chamado temos 2 regras
    ///1-no momento do cadastro do chamado , já devemos informar de qual cliente é;
    ///2- no momento do cadastro o chamado, a principio vamos fazer esse cadastro sem
    /// sem estar atribuido a um funcionário
    //3- no momento do cadastro do chamado , o status desse chamado dever estar RECEBIDO
    /// SERVIÇO PARA CaDASTRO DE NOVO CHAMADO

    public Chamado cadastrarChamado(Chamado chamado, Integer idCliente) {
        //REGRA 3
        chamado.setStatus(StatusChamado.RECEBIDO);
        //REGRA 2
        chamado.setFuncionario(null);
        //REGRA 1
        Optional<Cliente> cliente = clienteRepository.findById(idCliente);
        chamado.setCliente(cliente.get());
        return chamadoRepository.save(chamado);

    }

    // metodo para exlusão de um chamado

    public void excluirChamado(Integer idChamado) {
        chamadoRepository.deleteById(idChamado);
    }


    // metodo editar um chamado, no momento da edição de um chamado devemos preservar
    // o cliente e o funcionario desses chamado, vamos editar os dados mas continuamos
    // com os dados do cliente e funcionario.

    public Chamado editarChamado(Chamado chamado, Integer idChamado) {
        //instanciamos aqui um objeto do tipo Chamado para guardar os dados do chamados
        //sem as novas alteracoes PUT
        Chamado chamadoSemAsNovasAlteracoes = mostrarUmChamadoPeloId(idChamado);
        Funcionario funcionario = chamadoSemAsNovasAlteracoes.getFuncionario();
        Cliente cliente = chamadoSemAsNovasAlteracoes.getCliente();

        chamado.setCliente(cliente);
        chamado.setFuncionario(funcionario);
        return chamadoRepository.save(chamado);
    }
    //METODO para atribuir um funcionario determinado chamado
    // ou trocar o funcinário de determinado chamado
    //regra- no momento em que um determinado chamado é atribuido a um funcionario
    //       o status do chamado precisa ser alterado para ATRIBUIDO


    public Chamado atribuirFuncionario(Integer idChamado, Integer idFuncionario) {
        //buscar os dados do funcionario que vai ser atribuido a esses chamado
        Optional<Funcionario> funcionario = funcionarioRepository.findById(idFuncionario);
        //buscar o chamdo para qual vai ser especificado o funcionario escolhido
        Chamado chamado = mostrarUmChamadoPeloId(idChamado);
        chamado.setFuncionario(funcionario.get());
        chamado.setStatus(StatusChamado.ATRIBUIDO);
        return chamadoRepository.save(chamado);
    }


    //metodo pra modificar status de um chamado

    public Chamado modificarStatusService (Integer idChamado, String status) {
        Chamado chamado = mostrarUmChamadoPeloId(idChamado);

        if (chamado.getFuncionario() != null) {
            switch (status) {
                case "ATRIBUIDO":
                    chamado.setStatus(StatusChamado.ATRIBUIDO);
                    break;

                case "CONCLUIDO":
                    chamado.setStatus(StatusChamado.CONCLUIDO);
                    break;

                case "ARQUIVADO":
                    chamado.setStatus(StatusChamado.ARQUIVADO);
                    break;
            }
        }

        switch (status) {
            case "RECEBIDO":
                chamado.setStatus(StatusChamado.RECEBIDO);
                break;

        }


        return chamadoRepository.save(chamado);
    }
}



