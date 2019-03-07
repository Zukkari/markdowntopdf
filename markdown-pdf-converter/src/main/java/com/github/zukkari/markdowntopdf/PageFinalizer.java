package com.github.zukkari.markdowntopdf;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PageFinalizer {

    public String finalizePage(String cssFile, String html) {

        return "<html><head><meta charset=\"UTF-8\"><style>" + readStyle(cssFile) +
                "</style></head><body>" +
                html +
                "</body></html>";
    }

    private String readStyle(String cssFile) {
        try {
            byte[] cssBytes = Files.readAllBytes(Paths.get(cssFile));
            return new String(cssBytes, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
