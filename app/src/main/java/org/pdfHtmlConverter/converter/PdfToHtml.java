package org.pdfHtmlConverter.converter;

import java.io.File;
import java.io.IOException;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.pdfHtmlConverter.Utils;
import org.w3c.dom.Document;

import io.github.se_be.pdf2dom.PDFDomTree;

public class PdfToHtml implements Converter {
    private String path = null;

    public PdfToHtml(String arg) {
        path = arg;
    }

    @Override
    public void convert() {
        if (path == null) throw new IllegalArgumentException("Expected file path");
        if (!Utils.isValidExistingFile(path)) throw new IllegalArgumentException("Invalid file path");

        String outputPath = Utils.changeFileExtension(path, "html");
        try {
            PDDocument pdf = Loader.loadPDF(new File(path));
            PDFDomTree parser = new PDFDomTree();
            Document dom = parser.createDOM(pdf);
            
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(new DOMSource(dom), new StreamResult(new File(outputPath)));
            System.out.println("Conversion complete: " + outputPath);
        } catch (IOException | TransformerException e) {
            System.out.println("Error converting to HTML");
            System.out.println(e.getMessage());
        }
    }
}