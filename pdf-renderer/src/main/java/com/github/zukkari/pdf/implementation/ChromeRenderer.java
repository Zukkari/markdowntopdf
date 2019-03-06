package com.github.zukkari.pdf.implementation;

import com.github.zukkari.pdf.ChromeOptions;
import com.github.zukkari.pdf.PdfProcessor;
import com.github.zukkari.pdf.exception.RenderFailedException;

import java.io.File;
import java.io.InputStream;

public class ChromeRenderer implements PdfProcessor {

    private String chromeBinary;

    public ChromeRenderer(String chromeBinary) {
        this.chromeBinary = chromeBinary;
    }

    @Override
    public InputStream render(String id, File inputFile) {
        try {
            ProcessBuilder pb = new ProcessBuilder(chromeBinary,
                    ChromeOptions.HEADLESS,
                    ChromeOptions.DISABLE_GPU,
                    ChromeOptions.ENABLE_LOGGING,
                    ChromeOptions.VERBOSITY_1,
                    ChromeOptions.printToPdf(id),
                    inputFile.toPath().toUri().toURL().toString())
                    .inheritIO();

            pb.start().waitFor();

            // Read into memory so we can delete the pdf
            return convert(id);
        } catch (Exception e) {
            throw new RenderFailedException(e);
        }
    }
}
