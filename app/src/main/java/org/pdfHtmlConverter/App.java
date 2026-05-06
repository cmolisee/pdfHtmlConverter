package org.pdfHtmlConverter;

import org.pdfHtmlConverter.converter.Converter;
import org.pdfHtmlConverter.factory.ConverterFactory;

public class App {
    public static void main(String[] args) {
        if (args.length > 1) {
            String arg = args[1];
            ConverterFactory.getConverter(arg).convert();
        } else {
            System.getLogger(App.class.getName()).log(
                System.Logger.Level.WARNING,
                "Usage: javac pdfHtmlConverter -- <filePath | rawHTML>"
            );
        }
    }
}
