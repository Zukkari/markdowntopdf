package com.github.zukkari.pdfrenderservice.stats.service;

import com.github.zukkari.pdfrenderservice.stats.data.Statistics;
import com.github.zukkari.pdfrenderservice.stats.repository.StatisticsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class StatsService {
    private static final Logger log = LoggerFactory.getLogger(StatsService.class);

    private StatisticsRepository repository;
    private StatsCache cache;

    @Autowired
    public StatsService(StatisticsRepository repository, StatsCache cache) {
        this.repository = repository;
        this.cache = cache;
    }

    public void increment() {
        Statistics stats = repository.findById(1L).map(Statistics::increment).orElseGet(() -> {
            Statistics s = new Statistics();
            return s.increment().increment();
        });

        repository.save(stats);
        log.info("New pdf generation count '{}'", stats.getCount());
    }

    public BigDecimal getCount() {
        return cache.getStatistics();
    }
}
