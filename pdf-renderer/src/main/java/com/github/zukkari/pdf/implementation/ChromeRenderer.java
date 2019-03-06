package com.github.zukkari.pdf.implementation;

import com.github.zukkari.pdf.ChromeOptions;
import com.github.zukkari.pdf.PdfProcessor;
import com.github.zukkari.pdf.exception.RenderFailedException;
import com.github.zukkari.pdf.util.Streams;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

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

    private InputStream convert(String id) throws IOException {
        File f = new File(id + ".pdf");
        try (InputStream fos = new FileInputStream(f)) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            Streams.copy(fos, out);
            ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());

            // Delete the PDF to save the space ^_^
            Files.delete(f.toPath());

            return in;
        }
    }
}
