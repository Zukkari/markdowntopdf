package com.github.zukkari.pdfrenderservice;

import com.github.zukkari.markdowntopdf.MarkdownConverter;
import com.github.zukkari.markdowntopdf.implementation.Flexmark;
import com.github.zukkari.pdf.PdfProcessor;
import com.github.zukkari.pdf.implementation.CommandLineProcessor;
import com.github.zukkari.stats.client.StatisticsClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PdfRenderServiceApplication {
    private Logger log = LoggerFactory.getLogger(PdfRenderServiceApplication.class);

    @Value("${chrome.binary}")
    private String chromeBinary;

    @Value("${stats.host}")
    private String statsHost;

    @Value("${stats.token}")
    private String statsToken;

    public static void main(String[] args) {
        SpringApplication.run(PdfRenderServiceApplication.class, args);
    }

    @Bean
    public PdfProcessor commandLineProcessor() {
        log.info("Initializing command line pdf processor with binary path '{}'", chromeBinary);
        return new CommandLineProcessor(chromeBinary);
    }

    @Bean
    public MarkdownConverter flexmark() {
        log.info("Initializing markdown flexmark converter");
        return new Flexmark();
    }

    @Bean
    public StatisticsClient client() {
        log.info("Creating StatisticsClient with host {} and token {}", statsHost, statsToken);
        return new StatisticsClient(statsHost, statsToken);
    }
}
