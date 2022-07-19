package com.soulcode.Servicos.Controllers;


import com.soulcode.Servicos.Services.FuncionarioService;
import com.soulcode.Servicos.Util.UploadFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@CrossOrigin // comunicação entre portas diferentes /para não dar erro, dá  permissão para portas diferentes
@RestController
@RequestMapping("servicos")
public class UploadFileController {
  @Autowired
    FuncionarioService funcionarioService;

    @PostMapping("/funcionarios/envioFoto/{idFuncionario}")
    public ResponseEntity <Void> enviarFoto(@PathVariable Integer idFuncionario, MultipartFile file, @RequestParam("nome") String nome){

        String fileName = nome;
        String uploadDir = "C:/Users/Daiana/Desktop/fotoFunc"; //caminho das fotos qdo salvas
        String nomeCaminho = "C:/Users/Daiana/Desktop/fotoFunc/" + nome;

        try{
            UploadFile.saveFile(uploadDir,fileName, file);
            funcionarioService.salvarFoto(idFuncionario, nomeCaminho);
        } catch (IOException e) {
            System.out.println("o arquivo não foi enviado: " + e.getMessage());
        }
        return ResponseEntity.ok().build();
    }
}






