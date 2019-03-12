package com.github.zukkari.markdowntopdf;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PageFinalizer {
    private final String cssFile;
    private String style;

    public PageFinalizer(String cssFile) {
        this.cssFile = cssFile;
    }

    public String finalizePage(String html) {

        return "<html><head><meta charset=\"UTF-8\"><style>" + (style == null ? readStyle(cssFile) : style) +
                "</style></head><body>" +
                html +
                "</body></html>";
    }

    private String readStyle(String cssFile) {
        try {
            byte[] cssBytes = Files.readAllBytes(Paths.get(cssFile));
            style = new String(cssBytes, StandardCharsets.UTF_8);
            return style;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
