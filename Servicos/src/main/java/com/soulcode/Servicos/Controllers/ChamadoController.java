package com.soulcode.Servicos.Controllers;

import com.soulcode.Servicos.Models.Chamado;
import com.soulcode.Servicos.Models.Cliente;
import com.soulcode.Servicos.Models.Funcionario;
import com.soulcode.Servicos.Services.ChamadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.xml.crypto.Data;
import java.net.URI;
import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("servicos")

public class ChamadoController {

    @Autowired
    ChamadoService chamadoService;

    @GetMapping("/chamados")
    public List<Chamado> mostrarTodosChamados(){
        List<Chamado> chamados = chamadoService.mostrarTodosChamados();
        return chamados;
    }

    @GetMapping("/chamados/{idChamado}")
    public ResponseEntity<Chamado> mostrarUmChamadoPeloId (@PathVariable Integer idChamado){
        Chamado chamado = chamadoService.mostrarUmChamadoPeloId(idChamado);
        return ResponseEntity.ok().body(chamado);
    }

    @GetMapping("/chamadosPeloCliente/{idCliente}")
    public List<Chamado> buscarChamadosPeloCliente(@PathVariable Integer idCliente){
        List<Chamado> chamados = chamadoService.buscarChamadosPeloCliente(idCliente);
        return chamados;
    }

 /// aqui pedir para executar no post
    @GetMapping("/chamadosPeloFuncionario/{idFuncionario}")
    public List<Chamado> buscarChamadosPeloFuncionario(@PathVariable Integer idFuncionario){
        List<Chamado> chamados = chamadoService.buscarChamadosPeloFuncionario(idFuncionario);
        return chamados;
    }


    // criado manualmente no Repository por este motivo está diferente dos outros.
    @GetMapping("/chamadosPeloStatus")
    public List<Chamado> buscarChamadoPeloSatus(@RequestParam("status") String status){
        List<Chamado> chamados = chamadoService.buscarChamadoPeloStatus(status);
        return chamados;

    }

    @GetMapping("/chamadosPorIntervaloData")  //date tem que importar
    public List<Chamado> buscarPorIntervaloData(@RequestParam("data1") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date data1,
                                                @RequestParam("data2") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date data2){
        List<Chamado> chamados = chamadoService.buscarPorIntervaloData(data1,data2);
        return chamados;
    }

    /// FAZER MAPEAMENTO , vamos definir o endpoint para o serviço de cadastro de um novo chamado
    /// para cadastro precisamos anotar como metodo http-post

    @PostMapping("/chamados/{idCliente}")
    public ResponseEntity<Chamado> cadastrarChamado(@PathVariable Integer idCliente,
                                                    @RequestBody Chamado chamado){
        chamado = chamadoService.cadastrarChamado(chamado,idCliente);
        //nesse momento o chamado já foi cadastrado no dta base
        // precisamos agora criar o caminho uri pra esse novo chamado possa ser acessado.
        URI novaUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}") //importar URI dois
                .buildAndExpand(chamado.getIdChamado()).toUri();
        return ResponseEntity.created(novaUri).body(chamado);
    }

    // vamos mapear o serviço de excluir o chamado

    @DeleteMapping ("/chamados/{idChamado}")
    public ResponseEntity<Void> excluirChamado (@PathVariable Integer idChamado){
        chamadoService.excluirChamado(idChamado);
        return ResponseEntity.noContent().build();

    }

    /// vamos mapear o serviço de um chamado, quando nos vamos para edição
    // metodo http

    @PutMapping("/chamados/{idChamado}")
    public ResponseEntity <Chamado> editarChamado (@PathVariable Integer idChamado,
                                                   @RequestBody Chamado chamado){
        chamado.setIdChamado(idChamado);
        chamadoService.editarChamado(chamado , idChamado);
        return ResponseEntity.ok().build();
    }

    //vamos o fazer o mapeamento metodo atribuir um funcionario EDITANDO

    @PutMapping("/chamadosAtribuirFuncionario/{idChamado}/{idFuncionario}")
    public ResponseEntity<Chamado> atribuirFuncionario(@PathVariable Integer idChamado,
                                                       @PathVariable Integer idFuncionario){
        chamadoService.atribuirFuncionario(idChamado,idFuncionario);
        return ResponseEntity.noContent().build();
    }

    // vamos contruir mapeamento do metodo para modificar status de um chamado


  /// AQUI FALTA ULTMO CÓDIGO

    @PutMapping("/chamadosModificarStatus/{idChamado}")
    public ResponseEntity<Chamado> modificarStatus(@PathVariable Integer idChamado,
                                                   @RequestParam("status") String status) {

        Chamado chamado = chamadoService.modificarStatusService(idChamado,status);
        return ResponseEntity.ok().body(chamado);
    }




}
