package com.github.zukkari.pdfstatsservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class PdfRenderCountServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PdfRenderCountServiceApplication.class, args);
    }

}
