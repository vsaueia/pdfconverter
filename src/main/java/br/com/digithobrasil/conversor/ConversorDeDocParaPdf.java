package br.com.digithobrasil.conversor;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

@Component
public class ConversorDeDocParaPdf implements ConversorDeArquivosParaPdf {

    @Override
    public byte[] converter(MultipartFile arquivoOriginal) throws Exception {
        InputStream inputStream = new ByteArrayInputStream(arquivoOriginal.getBytes());
        HWPFDocument doc = new HWPFDocument(inputStream);
        ByteArrayOutputStream pdfDeSaidaOutputStream = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, pdfDeSaidaOutputStream);
        WordExtractor wordExtractor = new WordExtractor(doc);

        document.open();
        writer.setPageEmpty(true);
        document.newPage();
        writer.setPageEmpty(true);

        String[] paragraphs = wordExtractor.getParagraphText();
        for (int i = 0; i < paragraphs.length; i++) {
            document.add(new Paragraph(paragraphs[i]));
        }

        document.close();
        return pdfDeSaidaOutputStream.toByteArray();
    }
}
