package org.pdfHtmlConverter;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AppTest {
    private final PrintStream originalOut = System.out;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
    }
    
    @Test
    @DisplayName("🧪 Print Usage to terminal")
    void appPrintsUsage() {
        App.main(new String[]{});
        assertEquals("Usage: ./gradlew run --args=\"<p>test</p>\"\n", outContent.toString());
    }
}
