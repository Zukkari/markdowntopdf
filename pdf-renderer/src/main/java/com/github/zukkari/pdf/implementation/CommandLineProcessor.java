package com.github.zukkari.pdf.implementation;

import com.github.zukkari.pdf.ChromeOptions;
import com.github.zukkari.pdf.PdfProcessor;
import com.github.zukkari.pdf.exception.RenderFailedException;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
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

            return new FileInputStream(outFileId + ".pdf");
        } catch (Exception e) {
            throw new RenderFailedException(e);
        }
    }
}
