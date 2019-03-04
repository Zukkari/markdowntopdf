package com.github.zukkari.pdf;

import com.github.zukkari.pdf.implementation.CommandLineProcessor;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        String chromeBinary = "/usr/bin/google-chrome";
        PdfProcessor processor = new CommandLineProcessor(chromeBinary, new File("pdf-renderer/in.html"));

        processor.render();
    }
}
