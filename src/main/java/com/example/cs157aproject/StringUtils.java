package com.example.cs157aproject;

public class StringUtils {
    public static String sanitizeInput(String input) {
        if (input == null) {
            return null;
        }

        // Escape any SQL special characters to prevent injection attacks
        String sanitized = input.replaceAll("([\"'\\\\;\\-/*`])", "\\\\$1");

        // Trim any leading or trailing spaces
        sanitized = sanitized.trim();

        return sanitized;
    }
}
