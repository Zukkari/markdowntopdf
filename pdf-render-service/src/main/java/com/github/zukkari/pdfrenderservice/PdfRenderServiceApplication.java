package com.github.zukkari.pdfrenderservice;

import com.github.zukkari.markdowntopdf.MarkdownConverter;
import com.github.zukkari.markdowntopdf.implementation.Flexmark;
import com.github.zukkari.pdf.PdfProcessor;
import com.github.zukkari.pdf.implementation.PhantomRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableEurekaClient
@SpringBootApplication
public class PdfRenderServiceApplication {
    private Logger log = LoggerFactory.getLogger(PdfRenderServiceApplication.class);

    @Value("${chrome.binary:}")
    private String chromeBinary;

    @Value("${phantom.binary:}")
    private String phantomBinary;

    @Value("${phantom.script:}")
    private String phantomScript;

    @Value("${phantom.style:}")
    private String phantomStyle;


    public static void main(String[] args) {
        SpringApplication.run(PdfRenderServiceApplication.class, args);
    }

    @Bean
    public PdfProcessor pdfProcessor() {
        log.info("Initializing command line pdf processor with binary path '{}'", chromeBinary);
        return new PhantomRenderer(phantomBinary, phantomScript);
    }

    @Bean
    public MarkdownConverter flexmark() {
        log.info("Initializing markdown flexmark converter");
        return new Flexmark(phantomStyle);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
