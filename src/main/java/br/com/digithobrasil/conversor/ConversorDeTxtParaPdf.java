package br.com.digithobrasil.conversor;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;

@Component
public class ConversorDeTxtParaPdf implements ConversorDeArquivosParaPdf{
    @Override
    public byte[] converter(MultipartFile arquivoOriginal) throws Exception {
        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, baos);
        writer.open();
        document.open();

        BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(arquivoOriginal.getBytes())));
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
        return baos.toByteArray();
    }
}
