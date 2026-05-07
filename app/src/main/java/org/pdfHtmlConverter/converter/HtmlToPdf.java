package org.pdfHtmlConverter.converter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Path;

import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.pdfHtmlConverter.Utils;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

public class HtmlToPdf implements Converter {
    private String fileOrHtml = null;

    public HtmlToPdf(String arg) {
        fileOrHtml = arg;
    }

    @Override
    public void convert() throws IllegalArgumentException {
        if (fileOrHtml == null) throw new IllegalArgumentException("Expected file path or HTML");

        Boolean isHtml = Utils.isHtml(fileOrHtml);
        String outputPath = isHtml ? Path.of(System.getProperty("user.home"), "Downloads", "converted.pdf").toString() : Path.of(Utils.getFileDirectory(fileOrHtml), Utils.getFileName(fileOrHtml)).toString();
        try (OutputStream os = new FileOutputStream(outputPath)) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            if (isHtml) {
                org.jsoup.nodes.Document jsoupDoc = Jsoup.parse(fileOrHtml, "UTF-8");
                jsoupDoc.outputSettings().syntax(org.jsoup.nodes.Document.OutputSettings.Syntax.xml);
                builder.withW3cDocument(new W3CDom().fromJsoup(jsoupDoc), "/");
            } else {
                File htmlFile = new File(new URL(fileOrHtml).getPath());
                org.jsoup.nodes.Document jsoupDoc = Jsoup.parse(new File(new URL(fileOrHtml).getPath()), "UTF-8");
                builder.withW3cDocument(new W3CDom().fromJsoup(jsoupDoc), htmlFile.toURI().toString());
            }
            builder.toStream(os);
            builder.run();
            
            System.out.println("Conversion complete: " + outputPath);
        } catch (Exception e) {
            System.out.println("Unable to convert to PDF");
            System.out.println(e.getMessage());
        }
    }
    
}