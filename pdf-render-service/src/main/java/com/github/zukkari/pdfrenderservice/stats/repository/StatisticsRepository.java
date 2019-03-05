package com.github.zukkari.pdfrenderservice.stats.repository;

import com.github.zukkari.pdfrenderservice.stats.data.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticsRepository extends JpaRepository<Statistics, Long> {
}
