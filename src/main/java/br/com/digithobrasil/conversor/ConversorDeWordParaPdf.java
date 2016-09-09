package br.com.digithobrasil.conversor;

import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

@Component
public class ConversorDeWordParaPdf implements ConversorDeArquivosParaPdf {

    @Override
    public byte[] converter(MultipartFile arquivoOriginal) throws Exception {
        InputStream inputStream = new ByteArrayInputStream(arquivoOriginal.getBytes());
        XWPFDocument docx = new XWPFDocument(inputStream);
        PdfOptions options = null;
        ByteArrayOutputStream pdfDeSaidaOutputStream = new ByteArrayOutputStream();
        PdfConverter.getInstance().convert(docx, pdfDeSaidaOutputStream, options);
        return pdfDeSaidaOutputStream.toByteArray();
    }
}
