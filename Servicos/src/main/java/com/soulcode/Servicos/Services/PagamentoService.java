package com.soulcode.Servicos.Services;

import com.soulcode.Servicos.Models.Chamado;
import com.soulcode.Servicos.Models.EnderecoCliente;
import com.soulcode.Servicos.Models.Pagamento;
import com.soulcode.Servicos.Models.StatusPagamento;
import com.soulcode.Servicos.Repositories.ChamadoRepository;
import com.soulcode.Servicos.Repositories.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
public class PagamentoService {

    @Autowired
    PagamentoRepository pagamentoRepository;

    @Autowired
    ChamadoRepository chamadoRepository;
    @Cacheable("pagamentosCache")
    public List<Pagamento> mostrarTodosPagamentos() {
        return pagamentoRepository.findAll();
    }

    @Cacheable( value = "pagamentosCache", key = "#idPagamento")
    public Pagamento mostrarPagamentoPeloId(Integer idPagamento) {
        Optional<Pagamento> pagamento = pagamentoRepository.findById(idPagamento);
        return pagamento.orElseThrow();
    }

    @Cacheable( value = "pagamentosCache", key = "#status")
    public List<Pagamento> mostrarPagamentosPeloStatus(String status){
        return pagamentoRepository.findByStatus(status);
    }



    // CREATE. SERVIÇO 03: Cadastrar um pagamento
    // Regra 02: No momento do cadastro do pagamento, é preciso passar o id do chamado dono deste pagamento
    // Regra 03: O id do pagamento é o mesmo id do chamado
    // Regra 04: Não permitir que um pagamento seja salvo sem a existência do respectivo chamado
    @Cacheable( value = "pagamentosCache", key = "#idChamado")
    public Pagamento cadastrarPagamento(Pagamento pagamento, Integer idChamado){
        Optional<Chamado> chamado = chamadoRepository.findById(idChamado);
        if (chamado.isPresent()){
            pagamento.setIdPagamento(idChamado);
            pagamento.setStatus(StatusPagamento.LANCADO);
            pagamentoRepository.save(pagamento);

            chamado.get().setPagamento(pagamento);
            chamadoRepository.save(chamado.get());
            return pagamento;
        }else{
            throw new RuntimeException();
        }

    }
    @CachePut(value = "pagamentosCache", key = "#pagamento.idPagamento")
    public Pagamento editarPagamento(Pagamento pagamento){
        return pagamentoRepository.save(pagamento);
    }

    @CachePut(value = "pagamentosCache", key = "#idPagamento")
    public Pagamento modificarStatusPagamento(Integer idPagamento,String status){
        Pagamento pagamento = mostrarPagamentoPeloId(idPagamento);

        switch (status){
            case "LANCADO":
                pagamento.setStatus(StatusPagamento.LANCADO);
                break;
            case "QUITADO":
                pagamento.setStatus(StatusPagamento.QUITADO);
                break;
        }
        return pagamentoRepository.save(pagamento);
    }
    @Cacheable( value = "pagamentosCache")
    public List<List> orcamentoComServicoCliente(){
        return pagamentoRepository.orcamentoCOmServicoCliente();
    }
}






