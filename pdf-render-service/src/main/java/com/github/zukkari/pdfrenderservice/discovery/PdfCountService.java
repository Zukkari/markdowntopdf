package com.github.zukkari.pdfrenderservice.discovery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PdfCountService {
    private static final Logger log = LoggerFactory.getLogger(PdfCountService.class);

    private RestTemplate restTemplate;

    @Autowired
    public PdfCountService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void incrementPdfCount() {
        log.info("Started pdf count increment request");
        long start = System.currentTimeMillis();
        try {
            restTemplate.postForLocation("http://RenderCountService/v1/stats", null);
        } catch (Exception e) {
            log.error("Error when incrementing pdf count", e);
            throw e;
        }

        long end = System.currentTimeMillis();
        log.info("Pdf incrementation request finished in {} ms.", end - start);
    }
}
