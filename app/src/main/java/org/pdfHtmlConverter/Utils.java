package org.pdfHtmlConverter;

import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public static final String HTML_PATTERN = "<(\"[^\"]*\"|'[^']*'|[^'\">])*>";

    public static boolean isHtml(String text) {
        if (text == null) return false;
        Pattern pattern = Pattern.compile(HTML_PATTERN, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher matcher = pattern.matcher(text);
        return matcher.find();
    }

    public static boolean isValidExistingFile(String pathString) {
        try {
            Path path = Paths.get(pathString);
            return Files.exists(path) && Files.isRegularFile(path);
        } catch (InvalidPathException | NullPointerException e) {
            return false;
        }
    }

    public static String getFileName(String pathString) throws IllegalArgumentException {
        try {
            Path path = Paths.get(pathString);
            return path.getFileName().toString();
        } catch (InvalidPathException | NullPointerException e) {
            throw new IllegalArgumentException("pathString is null or invalid");
        }
    }

    public static String getFileDirectory(String pathString) throws IllegalArgumentException {
        try {
            Path path = Paths.get(pathString);
            return path.getParent().toString();
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("pathString is null or invalid");
        }
    }

    public static String changeFileExtension(String pathString, String newExtension) {
        Path path = Paths.get(pathString);
        String fileName = path.getFileName().toString();
        
        int lastDot = fileName.lastIndexOf('.');
        String baseName = (lastDot == -1) ? fileName : fileName.substring(0, lastDot);
        
        return path.resolveSibling(baseName + "." + newExtension).toString();
    }
}