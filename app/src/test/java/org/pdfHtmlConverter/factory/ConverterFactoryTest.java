package org.pdfHtmlConverter.factory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.pdfHtmlConverter.converter.HtmlToPdf;
import org.pdfHtmlConverter.converter.PdfToHtml;

class ConverterFactoryTest { 
    @TempDir
    Path tempDir;
     
    @Test
    @DisplayName("🧪 Return HtmlToPdf instance given raw HTML")
    void getsHtmlToPdfInstanceFromHtml() {
        assertEquals(HtmlToPdf.class, ConverterFactory.getConverter("<p>this is html</p>").getClass());
    }

    @Test
    @DisplayName("🧪 Return HtmlToPdf instance given file with .html extension")
    void getsHtmlToPdfInstanceFromFile() throws IOException {
        Path subDir = tempDir.resolve("example");
        Files.createDirectory(subDir);
        Path testFile = subDir.resolve("test.html");
        Files.createFile(testFile);
        
        assertEquals(HtmlToPdf.class, ConverterFactory.getConverter(testFile.toString()).getClass());
    }

    @Test
    @DisplayName("🧪 Return PdfToHtml instance given file with .pdf extension")
    void getsPdfToHtmlInstanceFromFile() throws IOException {
        Path subDir = tempDir.resolve("example");
        Files.createDirectory(subDir);
        Path testFile = subDir.resolve("test.pdf");
        Files.createFile(testFile);
        
        assertEquals(PdfToHtml.class, ConverterFactory.getConverter(testFile.toString()).getClass());
    }

    @Test
    @DisplayName("🧪 Throws IllegalArgumentException given null argument")
    void getFileDirectoryIllegalArgumentExceptionGivenNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ConverterFactory.getConverter(null);
        });

        assertEquals("Expected file path or HTML", exception.getMessage());
    }

    @Test
    @DisplayName("🧪 Throws IllegalArgumentException given not a file and not html")
    void getFileDirectoryIllegalArgumentExceptionGivenString() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ConverterFactory.getConverter("str");
        });

        assertEquals("Invalid file path or HTML", exception.getMessage());
    }

    @Test
    @DisplayName("🧪 Throws UnsupportedOperationException given invalid extension")
    void getFileDirectoryIllegalArgumentException() throws IOException {
        Path subDir = tempDir.resolve("example");
        Files.createDirectory(subDir);
        Path testFile = subDir.resolve("test.xml");
        Files.createFile(testFile);

        Exception exception = assertThrows(UnsupportedOperationException.class, () -> {
            ConverterFactory.getConverter(testFile.toString());
        });

        assertEquals("Unsupported extension: xml", exception.getMessage());
    }
}
