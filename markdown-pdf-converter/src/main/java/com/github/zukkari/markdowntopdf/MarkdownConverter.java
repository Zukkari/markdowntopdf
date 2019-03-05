package com.github.zukkari.markdowntopdf;

import com.github.zukkari.pdf.PdfProcessor;
import com.github.zukkari.pdf.implementation.CommandLineProcessor;
import com.github.zukkari.pdf.util.Streams;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public abstract class MarkdownConverter {

    private static final String CHROME_BINARY = System.getProperty("chrome.binary");

    /**
     * Convert provided markdown input stream to html
     * @param os markdown stream
     * @return html stream
     */
    protected abstract InputStream render(InputStream os) throws IOException;

    /**
     * Convert provided markdown stream into pdf
     * @param is markdown stream
     * @return pdf stream
     * @throws IOException rendering failed / binary not found
     */
    public final InputStream convert(PdfProcessor processor, InputStream is) throws IOException {
        InputStream html = render(is);
        File serialized = write(html);

        InputStream render = processor.render();

        Files.delete(serialized.toPath());

        return render;
    }

    private File write(InputStream html) throws IOException {
        Path f = Files.createFile(Paths.get(UUID.randomUUID().toString() + ".html"));
        try (OutputStream fos = new FileOutputStream(f.toFile())) {
            Streams.copy(html, fos);
        } finally {
            html.close();
        }

        return f.toFile();
    }


}
