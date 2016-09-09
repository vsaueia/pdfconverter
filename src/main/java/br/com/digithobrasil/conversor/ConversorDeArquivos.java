package br.com.digithobrasil.conversor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ConversorDeArquivos {

    @Autowired
    private ConversorDeTxtParaPdf conversorDeTxtParaPdf;
    @Autowired
    private ConversorDeImagemParaPdf conversorDeImagemParaPdf;
    @Autowired
    private ConversorDeWordParaPdf conversorDeWordParaPdf;

    public byte[] converterParaPdf(MultipartFile arquivoOriginal) throws Exception {
        if (ehArquivoDeTexto(arquivoOriginal)) {
            return conversorDeTxtParaPdf.converter(arquivoOriginal);
        } else if (ehArquivoDoWord(arquivoOriginal)) {
            return conversorDeWordParaPdf.converter(arquivoOriginal);
        } else {
            return conversorDeImagemParaPdf.converter(arquivoOriginal);
        }
    }

    private boolean ehArquivoDoWord(MultipartFile arquivoOriginal) {
        final String nomeDoArquivo = arquivoOriginal.getOriginalFilename();
        return nomeDoArquivo.endsWith("doc") || nomeDoArquivo.endsWith("docx");
    }

    private boolean ehArquivoDeTexto(MultipartFile arquivoOriginal) {
        return arquivoOriginal.getOriginalFilename().endsWith("txt");
    }
}
