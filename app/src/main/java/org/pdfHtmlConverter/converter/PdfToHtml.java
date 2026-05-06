package org.pdfHtmlConverter.converter;

import java.io.File;
import java.io.IOException;
import java.lang.System.Logger;

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
    private String arg = null;
    private static final Logger LOGGER = System.getLogger(PdfToHtml.class.getName());

    public PdfToHtml(String argString) {
        arg = argString;
    }

    @Override
    public void convert() {
        if (arg == null) throw new IllegalArgumentException("Expected file path");
        if (!Utils.isValidExistingFile(arg)) throw new IllegalArgumentException("Invalid file path");

        String outputPath = "./" + Utils.getFileName(arg) + ".html";
        try {
            PDDocument pdf = Loader.loadPDF(new File(arg));
            PDFDomTree parser = new PDFDomTree();
            Document dom = parser.createDOM(pdf);
            
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(new DOMSource(dom), new StreamResult(new File(outputPath)));
            LOGGER.log(System.Logger.Level.INFO, () -> "Conversion complete: " + outputPath);
        } catch (IOException | TransformerException e) {
            LOGGER.log(System.Logger.Level.ERROR, "Error converting to HTML", e);
        }
    }
}