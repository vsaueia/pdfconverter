package br.com.digithobrasil.controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@SpringBootApplication
@RestController
public class ConversorDePdfController {

    private static final Logger LOG = Logger.getLogger(ConversorDePdfController.class);

    @RequestMapping(value = "/arquivo", method = RequestMethod.POST)
    public @ResponseBody byte[] converterArquivo (@RequestParam(value="file", required=true) MultipartFile file ) {

        try {
            Document document = new Document();
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                PdfWriter writer = PdfWriter.getInstance(document, baos);
                writer.open();
                document.open();
                document.add(Image.getInstance(file.getBytes()));
                document.close();
                writer.close();
                return baos.toByteArray();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        } catch (RuntimeException e) {
            LOG.error("Error while uploading.", e);
            throw e;
        } catch (Exception e) {
            LOG.error("Error while uploading.", e);
            throw new RuntimeException(e);
        }
        return null;
    }


    public static void main(String[] args) {
        SpringApplication.run(ConversorDePdfController.class, args);
    }

}
