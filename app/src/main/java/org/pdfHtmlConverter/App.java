package org.pdfHtmlConverter;

import org.pdfHtmlConverter.factory.ConverterFactory;

public class App {
    public static void main(String[] args) {
        if (args.length == 1) {
            String arg = args[0];
            ConverterFactory.getConverter(arg).convert();
        } else {
            System.out.println("Usage: ./gradlew run --args=\"<p>test</p>\"");
        }
    }
}
