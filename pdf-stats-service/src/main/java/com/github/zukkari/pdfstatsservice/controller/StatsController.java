package com.github.zukkari.pdfstatsservice.controller;

import com.github.zukkari.pdfstatsservice.stats.data.RenderCountDTO;
import com.github.zukkari.pdfstatsservice.stats.service.RenderCountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/stats")
public class StatsController {
    private static final Logger log = LoggerFactory.getLogger(StatsController.class);

    private RenderCountService service;

    @Autowired
    public StatsController(RenderCountService service) {
        this.service = service;
    }

    @GetMapping
    public RenderCountDTO stats() {
        RenderCountDTO dto = new RenderCountDTO();
        dto.setCount(service.getCount());
        return dto;
    }

    @PostMapping
    public void update() {
        log.info("Request received, incrementing generated pdf count");
        service.increment();
    }
}
