package org.pdfHtmlConverter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class UtilsTest {
    @TempDir
    Path tempDir;

    @Test
    @DisplayName("🧪 Return true or false given string is HTML")
    void isHtmlTest() throws IOException {
        assertTrue(Utils.isHtml("<!Doctype html>"));
        assertTrue(Utils.isHtml("<p>This is some text</p>"));
        assertFalse(Utils.isHtml("This is not HTML"));
        assertFalse(Utils.isHtml("if (1 < 5)"));
    }
    
    @Test
    @DisplayName("🧪 Return true or false given file exists")
    void isValidExistingFileTest() throws IOException {
        Path subDir = tempDir.resolve("example");
        Files.createDirectory(subDir);
        Path testFile = subDir.resolve("test.html");
        Files.createFile(testFile);
        
        assertTrue(Utils.isValidExistingFile(testFile.toString()));
        assertFalse(Utils.isValidExistingFile("bad/path/test.html"));
    }

    @Test
    @DisplayName("🧪 Return filename given valid path")
    void getFileNamePass() throws IOException {
        Path subDir = tempDir.resolve("example");
        Files.createDirectory(subDir);
        Path testFile = subDir.resolve("test.html");
        Files.createFile(testFile);
        
        assertEquals("test.html", Utils.getFileName(testFile.toString()));
    }

    @Test
    @DisplayName("🧪 Throws IllegalArgumentException given null")
    void getFileNameIllegalArgumentException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Utils.getFileName(null);
        });

        assertEquals("pathString is null or invalid", exception.getMessage());
    }

    @Test
    @DisplayName("🧪 Return directory given valid path")
    void getFileDirectoryPass() throws IOException {
        Path subDir = tempDir.resolve("example");
        Files.createDirectory(subDir);
        Path testFile = subDir.resolve("test.html");
        Files.createFile(testFile);
        
        assertEquals(testFile.getParent().toString(), Utils.getFileDirectory(testFile.toString()));
    }

    @Test
    @DisplayName("🧪 Throws IllegalArgumentException given null")
    void getFileDirectoryIllegalArgumentException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Utils.getFileDirectory(null);
        });

        assertEquals("pathString is null or invalid", exception.getMessage());
    }

    @Test
    @DisplayName("🧪 Should change .pdf to .html")
    void changeFileExtensionToHtml() {
        assertEquals("/example/path/test.html", Utils.changeFileExtension("/example/path/test.pdf", "html"));
    }
}