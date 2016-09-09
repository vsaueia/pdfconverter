package br.com.digithobrasil.controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.log4j.Logger;
import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

@SpringBootApplication
@RestController
public class ConversorDePdfController {

    private static final Logger LOG = Logger.getLogger(ConversorDePdfController.class);

    @RequestMapping(value = "/converter", method = RequestMethod.POST)
    public
    @ResponseBody
    byte[] converterArquivo(@RequestParam(value = "arquivo", required = true) MultipartFile arquivoOriginal) {

        try {
            try {
                ByteArrayOutputStream pdfConvertido;
                if (ehArquivoDeTexto(arquivoOriginal)) {
                    pdfConvertido = conveterTxtParaPdf(arquivoOriginal.getBytes());
                } else if (ehArquivoDoWord(arquivoOriginal)) {
                    pdfConvertido = conveterDocParaPdf(arquivoOriginal.getBytes());
                } else {
                    pdfConvertido = converterImagemParaPdf(arquivoOriginal.getBytes());
                }

                // passo temporario só para teste
                escreverNoDisco(pdfConvertido.toByteArray());

                return pdfConvertido.toByteArray();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (RuntimeException e) {
            LOG.error("Error while uploading.", e);
            throw e;
        } catch (Exception e) {
            LOG.error("Error while uploading.", e);
            throw new RuntimeException(e);
        }
        throw new RuntimeException("Não foi possível converter o arquivo");
    }

    private boolean ehArquivoDoWord(MultipartFile arquivoOriginal) {
        final String nomeDoArquivo = arquivoOriginal.getOriginalFilename();
        return nomeDoArquivo.endsWith("doc") || nomeDoArquivo.endsWith("docx");
    }

    private boolean ehArquivoDeTexto(MultipartFile arquivoOriginal) {
        return arquivoOriginal.getOriginalFilename().endsWith("txt");
    }

    private ByteArrayOutputStream converterImagemParaPdf(byte[] bytes) throws DocumentException, IOException {
        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, baos);
        writer.open();
        document.open();
        document.add(Image.getInstance(bytes));
        document.close();
        writer.close();
        return baos;
    }

    private ByteArrayOutputStream conveterTxtParaPdf(byte[] bytes) throws Exception {
        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, baos);
        writer.open();
        document.open();

        BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(bytes)));
        String strLine;
        BaseFont courier = BaseFont.createFont(BaseFont.COURIER, BaseFont.CP1252, BaseFont.EMBEDDED);
        Font myfont = new Font(courier);
        while ((strLine = br.readLine()) != null) {
            Paragraph para = new Paragraph(strLine + "\n", myfont);
            para.setAlignment(Element.ALIGN_JUSTIFIED);
            document.add(para);
        }

        document.close();
        writer.close();
        return baos;
    }

    private ByteArrayOutputStream conveterDocParaPdf(byte[] bytes) throws DocumentException, IOException {
        InputStream inputStream = new ByteArrayInputStream(bytes);
        XWPFDocument docx = new XWPFDocument(inputStream);
        PdfOptions options = null;
        ByteArrayOutputStream pdfDeSaidaOutputStream = new ByteArrayOutputStream();
        PdfConverter.getInstance().convert(docx, pdfDeSaidaOutputStream, options);

        return pdfDeSaidaOutputStream;
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
        SpringApplication.run(ConversorDePdfController.class, args);
    }
}
