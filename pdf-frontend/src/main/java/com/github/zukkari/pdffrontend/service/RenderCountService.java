package com.github.zukkari.pdffrontend.service;

import com.github.zukkari.pdffrontend.service.entity.RenderCount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;

@Service
public class RenderCountService {
    private static final Logger log = LoggerFactory.getLogger(RenderCountService.class);

    @Value("${service.router}")
    private String router;

    @Value("${service.count.uri}")
    private String countUri;

    private RestTemplate template;

    @Autowired
    public RenderCountService(RestTemplate template) {
        this.template = template;
    }

    public BigInteger getCount() throws URISyntaxException {
        long start = System.currentTimeMillis();
        log.info("Started request for pdf count");

        RenderCount count = template.getForObject(new URI(router).resolve(countUri), RenderCount.class);

        long end = System.currentTimeMillis();
        log.info("Request for pdf count finished in {} ms", end - start);

        return count == null ? BigInteger.ZERO : count.getCount();
    }
}
