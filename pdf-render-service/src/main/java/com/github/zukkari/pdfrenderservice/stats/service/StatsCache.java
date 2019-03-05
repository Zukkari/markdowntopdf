package com.github.zukkari.pdfrenderservice.stats.service;

import com.github.zukkari.pdfrenderservice.stats.data.Statistics;
import com.github.zukkari.pdfrenderservice.stats.repository.StatisticsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Timer;
import java.util.TimerTask;

@Service
public class StatsCache {
    private static final Logger log = LoggerFactory.getLogger(StatsCache.class);

    private StatisticsRepository repository;

    private BigDecimal count;

    @Autowired
    public StatsCache(StatisticsRepository repository) {
        this.repository = repository;
        this.count = repository.findById(1L).map(Statistics::getCount).orElse(BigDecimal.ZERO);

        launchUpdater();
    }

    BigDecimal getStatistics() {
        return count;
    }

    private void launchUpdater() {
        new Timer().schedule(new TimerTask() {

            @Override
            public void run() {
                long start = System.currentTimeMillis();
                log.info("Running cached statistics update");

                count = repository.findById(1L).map(Statistics::getCount).orElse(BigDecimal.ZERO);

                long end = System.currentTimeMillis();
                log.info("Finished updating statistics in {} ms", end - start);
            }

        }, 30000, 30000);
    }

}
