package com.soulcode.Servicos.Controllers;

import com.soulcode.Servicos.Models.Funcionario;
import com.soulcode.Servicos.Services.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

//1 models, 2 Repository(interface), 3 Service, 4 controller

///criar endpoints o crud dos funcionarios mapear este serviço/caminho
///endpoints endereço onde vão ficar expostos
@CrossOrigin //aplicações de portas diferentes podem acessar por este motivo crossorigin (cors)
@RestController /// essa aplicação vai ser controller da API a camada que vai expor os serviços
@RequestMapping("servicos") // rota base vai ter que começar com serviços
public class FuncionarioController {

    @Autowired
    FuncionarioService funcionarioService;// aqui injeção de dependencia do funcionarioservice para controller

    @GetMapping("/funcionarios") /// rota busca dados
    public List<Funcionario> mostrarTodosFuncionarios(){ //aqui pode ser outro nome do metodo
        List<Funcionario> funcionarios = funcionarioService.mostrarTodosFuncionarios(); //criação lista funcionarios
        return funcionarios;
    }

    @GetMapping("/funcionarios/{idFuncionario}")
    public ResponseEntity<Funcionario> mostrarUmFuncionararioPeloId(@PathVariable Integer idFuncionario){
        Funcionario funcionario = funcionarioService.mostrarUmFuncionarioPeloId(idFuncionario);
        return ResponseEntity.ok().body(funcionario);
        //ResponseEntity(status cabeçalho/corpo/reposta completa 101/102/404) @pathVariable pela rota idfuncionario
    }




    @GetMapping("/funcionariosEmail/{email}") //foi adiconado Email para não dar erro na rota
    public ResponseEntity<Funcionario> mostrarUmFuncionarioPeloEmail(@PathVariable String email){
        Funcionario funcionario = funcionarioService.mostrarUmFuncionarioPeloEmail(email);
        return ResponseEntity.ok().body(funcionario);

    }  // hoje

    @GetMapping("funcionariosDoCargo/{idCargo}")
    public List<Funcionario> mostrarTodosFuncionariosDeUmCargo(@PathVariable Integer idCargo){
        List<Funcionario> funcionarios = funcionarioService.mostrarTodosFuncionariosDeUmCargo(idCargo);
        return funcionarios;
    }


    ///adicionando funcionario na tabela via post
    ///nessa linha 42, o funcionario já é salvo na tabela do dtabase
    // agora precisamos criar um uri para esse novo registro na tabela

    /// aqui foi inserido integer idFuncionario patvarivble

    @PostMapping("/funcionarios/{idCargo}")
    public ResponseEntity<Funcionario> cadastrarFuncionario(@PathVariable Integer idCargo,  @RequestBody Funcionario funcionario){
        funcionario = funcionarioService.cadastrarFuncionario(funcionario,idCargo);
        URI novaUri = ServletUriComponentsBuilder.fromCurrentRequest().path("id")
                .buildAndExpand(funcionario.getIdFuncionario()).toUri(); //funcionarios 31
        return ResponseEntity.created(novaUri).body(funcionario);

    }


    @DeleteMapping("/funcionarios/{idFuncionario}")
    public ResponseEntity<Void> excluirFuncionario(@PathVariable Integer idFuncionario){
        funcionarioService.exluirFuncionario(idFuncionario);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/funcionarios/{idFuncionario}")
    public ResponseEntity<Funcionario> editarFuncionario(@PathVariable Integer idFuncionario, @RequestBody Funcionario funcionario){
        funcionario.setIdFuncionario(idFuncionario);
        funcionarioService.editarFuncionario(funcionario);
        return ResponseEntity.ok().body(funcionario);
    }




}
