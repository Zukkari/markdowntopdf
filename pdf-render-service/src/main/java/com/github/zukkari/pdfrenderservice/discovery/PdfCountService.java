package com.github.zukkari.pdfrenderservice.discovery;

import com.github.zukkari.pdfrenderservice.discovery.exception.NoInstanceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@Service
public class PdfCountService {
    private static final Logger log = LoggerFactory.getLogger(PdfCountService.class);
    private static final String SERVICE_URI = "/pdf-stats/api/v1/stats";

    @Value("${router.service:Router}")
    private String router;

    private DiscoveryClient discoveryClient;
    private RestTemplate restTemplate;

    @Autowired
    public PdfCountService(DiscoveryClient discoveryClient, RestTemplate restTemplate) {
        this.discoveryClient = discoveryClient;
        this.restTemplate = restTemplate;
    }

    public void incrementPdfCount() {
        log.info("Started pdf count increment request");
        long start = System.currentTimeMillis();
        List<ServiceInstance> serviceInstances = discoveryClient.getInstances(router);
        if (serviceInstances.isEmpty()) {
            throw new NoInstanceException("No instances found for '" + router + "'");
        }
        log.info("Number of available router instances: '{}'", serviceInstances.size());

        ServiceInstance instance = serviceInstances.get(0);
        URI uri = instance.getUri().resolve(SERVICE_URI);
        restTemplate.postForLocation(uri, null);

        long end = System.currentTimeMillis();
        log.info("Pdf incrementation request finished in {} ms.", end - start);
    }
}
