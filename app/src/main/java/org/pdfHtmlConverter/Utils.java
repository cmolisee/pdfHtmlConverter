package org.pdfHtmlConverter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.InvalidPathException;

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
            throw new IllegalArgumentException("pathString is null or not a valid path");
        }
    }
}