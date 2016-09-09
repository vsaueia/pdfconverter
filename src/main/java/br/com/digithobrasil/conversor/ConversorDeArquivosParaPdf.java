package br.com.digithobrasil.conversor;

import org.springframework.web.multipart.MultipartFile;

public interface ConversorDeArquivosParaPdf {
    byte[] converter(MultipartFile arquivoOriginal) throws Exception;
}
