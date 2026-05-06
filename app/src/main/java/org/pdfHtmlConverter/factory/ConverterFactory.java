package org.pdfHtmlConverter.factory;

import org.pdfHtmlConverter.converter.Converter;
import org.pdfHtmlConverter.converter.HtmlToPdf;
import org.pdfHtmlConverter.converter.PdfToHtml;
import org.pdfHtmlConverter.Utils;

public class ConverterFactory {
    public static Converter getConverter(String argString) {
        if (argString == null) throw new IllegalArgumentException("Expected file path or HTML");
        if (!argString.contains(".") && !Utils.isHtml(argString)) {
            throw new IllegalArgumentException("Invalid file path or HTML");
        }

        String extension = Utils.isHtml(argString) ? "html" : argString.substring(argString.lastIndexOf(".") + 1).toLowerCase();
        return switch (extension) {
            case "html" -> new HtmlToPdf(argString);
            case "pdf" -> new PdfToHtml(argString);
            default -> throw new UnsupportedOperationException("Unsupported extension: " + extension);
        };
    }
}