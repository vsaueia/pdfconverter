package br.com.digithobrasil.conversor;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;

@Component
public class ConversorDeImagemParaPdf implements ConversorDeArquivosParaPdf{
    @Override
    public byte[] converter(MultipartFile arquivoOriginal) throws Exception {
        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, baos);
        writer.open();
        document.open();
        document.add(Image.getInstance(arquivoOriginal.getBytes()));
        document.close();
        writer.close();
        return baos.toByteArray();
    }

}
