package com.github.zukkari.pdfstatsservice.controller;

import com.github.zukkari.pdfstatsservice.stats.data.StatisticsDTO;
import com.github.zukkari.pdfstatsservice.stats.service.StatsService;
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

    private StatsService service;

    @Autowired
    public StatsController(StatsService service) {
        this.service = service;
    }

    @GetMapping
    public StatisticsDTO stats() {
        StatisticsDTO dto = new StatisticsDTO();
        dto.setCount(service.getCount());
        return dto;
    }

    @PostMapping
    public void update() {
        log.info("Request received, incrementing generated pdf count");
        service.increment();
    }
}
