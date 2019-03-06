package com.github.zukkari.pdf.implementation;

import com.github.zukkari.pdf.PdfProcessor;
import com.github.zukkari.pdf.exception.RenderFailedException;

import java.io.File;
import java.io.InputStream;

public class PhantomRenderer implements PdfProcessor {
    private String phantomBinary;
    private final String renderScript;

    public PhantomRenderer(String phantomBinary, String renderScript) {
        this.phantomBinary = phantomBinary;
        this.renderScript = renderScript;
    }

    @Override
    public InputStream render(String id, File inputFile) {
        try {
            Process proc = new ProcessBuilder(
                    phantomBinary,
                    renderScript,
                    id,
                    inputFile.toPath().toUri().toURL().toString())
                    .inheritIO()
                    .start();

            proc.waitFor();

            return convert(id);
        } catch (Exception e) {
            throw new RenderFailedException(e);
        }
    }
}
