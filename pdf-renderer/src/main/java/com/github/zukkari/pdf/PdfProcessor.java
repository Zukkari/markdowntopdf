package com.github.zukkari.pdf;

import com.github.zukkari.pdf.util.Streams;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public interface PdfProcessor {

    InputStream render(String id, File inputFile);

    default InputStream convert(String id) throws IOException {
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
