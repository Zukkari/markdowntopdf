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
import java.util.UUID;

public class CommandLineProcessor implements PdfProcessor {

    private String chromeBinary;
    private File inputFile;
    private String outFileId;

    public CommandLineProcessor(String chromeBinary, File inputFile) {
        this.outFileId = UUID.randomUUID().toString();
        this.chromeBinary = chromeBinary;
        this.inputFile = inputFile;
    }


    public InputStream render() {
        try {
            ProcessBuilder pb = new ProcessBuilder(chromeBinary,
                    ChromeOptions.HEADLESS,
                    ChromeOptions.DISABLE_GPU,
                    ChromeOptions.ENABLE_LOGGING,
                    ChromeOptions.VERBOSITY_1,
                    ChromeOptions.printToPdf(outFileId),
                    inputFile.toPath().toUri().toURL().toString())
                    .inheritIO();

            pb.start().waitFor();

            // Read into memory so we can delete the pdf
            return convert();
        } catch (Exception e) {
            throw new RenderFailedException(e);
        }
    }

    private InputStream convert() throws IOException {
        File f = new File(outFileId + ".pdf");
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
