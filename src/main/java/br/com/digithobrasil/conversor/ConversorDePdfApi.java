package br.com.digithobrasil.conversor;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@SpringBootApplication
@RestController
public class ConversorDePdfApi {

    private static final Logger LOG = Logger.getLogger(ConversorDePdfApi.class);

    @Autowired
    private ConversorDeArquivos conversorDeArquivos;

    @RequestMapping(value = "/converter", method = RequestMethod.POST)
    public @ResponseBody byte[] converterArquivo(@RequestParam(value = "arquivo", required = true) MultipartFile arquivoOriginal) {
        try {
            byte[] pdfConvertido = conversorDeArquivos.converterParaPdf(arquivoOriginal);

            // passo temporario só para teste
            escreverNoDisco(pdfConvertido);

            return pdfConvertido;
        } catch (Exception e) {
            LOG.error("Error while uploading.", e);
        }
        throw new RuntimeException("Não foi possível converter o arquivo");
    }


    private void escreverNoDisco(byte[] bytes) throws IOException {
        OutputStream outStream = null;
        ByteArrayOutputStream byteOutStream = null;
        try {
            outStream = new FileOutputStream("/home/vsaueia/Desktop/fileNameToSave.pdf");
            byteOutStream = new ByteArrayOutputStream();
            byteOutStream.write(bytes);
            byteOutStream.writeTo(outStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            outStream.close();
        }
    }


    public static void main(String[] args) {
        SpringApplication.run(ConversorDePdfApi.class, args);
    }
}
