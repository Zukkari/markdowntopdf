package com.github.zukkari.pdfrenderservice.controller;

import com.github.zukkari.pdfrenderservice.stats.data.StatisticsDTO;
import com.github.zukkari.pdfrenderservice.stats.service.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/stats")
public class StatsController {

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

}
