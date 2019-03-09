package com.github.zukkari.pdffrontend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;

@Service
public class RenderService {
    private static final Logger log = LoggerFactory.getLogger(RenderService.class);

    @Value("${service.router}")
    private String router;

    @Value("${service.render.uri}")
    private String renderUri;

    private RestTemplate restTemplate;

    @Autowired
    public RenderService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Resource render(byte[] payload, String fileName) throws URISyntaxException {
        try {
            HttpEntity<byte[]> content = new HttpEntity<>(payload);
            ResponseEntity<Resource> response = restTemplate.exchange(
                    router + renderUri,
                    HttpMethod.POST,
                    content,
                    Resource.class,
                    fileName);

            if (response.getStatusCode() != HttpStatus.OK) {
                throw new RuntimeException(String.format("Status code is not OK: %s", response.getStatusCode()));
            }

            return response.getBody();
        } catch (Exception e) {
            log.error("Error occurred when uploading file", e);
            throw e;
        }
    }
}
