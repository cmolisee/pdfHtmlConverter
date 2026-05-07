package org.pdfHtmlConverter.factory;

import org.pdfHtmlConverter.Utils;
import org.pdfHtmlConverter.converter.Converter;
import org.pdfHtmlConverter.converter.HtmlToPdf;
import org.pdfHtmlConverter.converter.PdfToHtml;

public class ConverterFactory {
    public static Converter getConverter(String arg) {
        if (arg == null) throw new IllegalArgumentException("Expected file path or HTML");
        if (!Utils.isHtml(arg) && !Utils.isValidExistingFile(arg)) {
            throw new IllegalArgumentException("Invalid file path or HTML");
        }

        String extension = Utils.isHtml(arg) ? "html" : arg.substring(arg.lastIndexOf(".") + 1).toLowerCase();
        return switch (extension) {
            case "html" -> new HtmlToPdf(arg);
            case "pdf" -> new PdfToHtml(arg);
            default -> throw new UnsupportedOperationException("Unsupported extension: " + extension);
        };
    }
}