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
    private ConversorDeDocxParaPdf conversorDeDocxParaPdf;
    @Autowired
    private ConversorDeDocParaPdf conversorDeDocParaPdf;

    public byte[] converterParaPdf(MultipartFile arquivoOriginal) throws Exception {
        if (ehArquivoDeTexto(arquivoOriginal)) {
            return conversorDeTxtParaPdf.converter(arquivoOriginal);
        } else if (ehArquivoDocx(arquivoOriginal)) {
            return conversorDeDocxParaPdf.converter(arquivoOriginal);
        } else if (ehArquivoDoc(arquivoOriginal)) {
            return conversorDeDocParaPdf.converter(arquivoOriginal);
        } else {
            return conversorDeImagemParaPdf.converter(arquivoOriginal);
        }
    }

    private boolean ehArquivoDocx(MultipartFile arquivoOriginal) {
        final String nomeDoArquivo = arquivoOriginal.getOriginalFilename();
        return nomeDoArquivo.endsWith("docx");
    }

    private boolean ehArquivoDoc(MultipartFile arquivoOriginal) {
        final String nomeDoArquivo = arquivoOriginal.getOriginalFilename();
        return nomeDoArquivo.endsWith("doc");
    }

    private boolean ehArquivoDeTexto(MultipartFile arquivoOriginal) {
        return arquivoOriginal.getOriginalFilename().endsWith("txt");
    }
}
