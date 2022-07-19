package com.soulcode.Servicos.Util;

// vai subir foto upload

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class UploadFile {

    ///rotina até o momento só existe no back/ vai ter criar metpoint mapear

    /// criado o metodo saveFile 3 parametros o MultipartFile baixar qualquer arquivo generico

    public static void saveFile(String uploadDir, String fileName, MultipartFile file) throws IOException {
       ///caminho / instaciar a classe upoloadPath

        Path uploadPath = Paths.get(uploadDir); ///leitura do caminho

        //verificar se ele já existe !negação
        if(!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath); ///adiconar metodo creatDirectories adicona em throws
        }

        ///fazer upload criado objeto inputStream
        /// vamos tentar fazer o upload do arquivo
        //InputStream tentar fazer a leitura do arquivo q estamos querendo subir
        // faz a leitura byte por byte
        try(InputStream inputStream = file.getInputStream()){

        //nesse momento o arquivo é salvo no diretório que passamos a assinatura
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        }
        catch (IOException e){
            throw new IOException("Não foi possível enviar o seu arquivo");
        }

    }

}
