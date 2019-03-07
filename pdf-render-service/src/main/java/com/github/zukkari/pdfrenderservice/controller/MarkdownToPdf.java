package com.github.zukkari.pdfrenderservice.controller;

import com.github.zukkari.markdowntopdf.MarkdownConverter;
import com.github.zukkari.pdf.PdfProcessor;
import com.github.zukkari.pdf.util.Streams;
import com.github.zukkari.pdfrenderservice.discovery.PdfCountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/")
public class MarkdownToPdf {
    private static final Logger log = LoggerFactory.getLogger(MarkdownToPdf.class);

    private PdfProcessor processor;
    private MarkdownConverter converter;
    private PdfCountService countService;

    @Autowired
    public MarkdownToPdf(PdfProcessor processor, MarkdownConverter converter, PdfCountService countService) {
        this.processor = processor;
        this.converter = converter;
        this.countService = countService;
    }

    @PostMapping("/render/{fileName}")
    public void convert(@PathVariable String fileName, HttpServletRequest request, HttpServletResponse response) throws IOException {
        InputStream in = copyToMemory(request);

        log.info("Rendering pdf from markdown file '{}'", fileName);
        long conversionStart = System.currentTimeMillis();

        long start = System.currentTimeMillis();
        InputStream pdf = converter.convert(processor, in);
        long end = System.currentTimeMillis();
        log.info("Markdown conversion finished in {} ms", end - start);

        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, attachment(fileName));
        Streams.copy(pdf, response.getOutputStream());
        response.flushBuffer();

        long conversionEnd = System.currentTimeMillis();
        log.info("Conversion finished in {} ms", conversionEnd - conversionStart);

        CompletableFuture.runAsync(() -> countService.incrementPdfCount());
    }

    private String attachment(String fileName) {
        String newFileName = fileName.replaceFirst("(\\..+)?$", ".pdf");
        log.info("New pdf file name '{}'", newFileName);
        return "attachment; filename=\"" + newFileName +"\"";
    }

    private InputStream copyToMemory(HttpServletRequest request) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Streams.copy(request.getInputStream(), out);
        return new ByteArrayInputStream(out.toByteArray());
    }

}
