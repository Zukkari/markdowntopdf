package com.github.zukkari.pdfstatsservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class PdfStatsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PdfStatsServiceApplication.class, args);
    }

}
