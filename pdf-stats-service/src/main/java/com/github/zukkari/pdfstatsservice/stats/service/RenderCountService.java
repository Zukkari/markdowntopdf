package com.github.zukkari.pdfstatsservice.stats.service;

import com.github.zukkari.pdfstatsservice.stats.data.RenderCount;
import com.github.zukkari.pdfstatsservice.stats.repository.RenderCountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class RenderCountService {
    private static final Logger log = LoggerFactory.getLogger(RenderCountService.class);

    private RenderCountRepository repository;
    @Autowired
    public RenderCountService(RenderCountRepository repository) {
        this.repository = repository;
    }

    public void increment() {
        RenderCount stats = repository.findById(1L).map(RenderCount::increment).orElseGet(() -> RenderCount.of(BigDecimal.ONE));

        repository.save(stats);
        log.info("New pdf generation count '{}'", stats.getCount());
    }

    @Cacheable(cacheNames = "count")
    public BigDecimal getCount() {
        return repository.findById(1L).map(RenderCount::getCount).orElse(BigDecimal.ZERO);
    }
}
