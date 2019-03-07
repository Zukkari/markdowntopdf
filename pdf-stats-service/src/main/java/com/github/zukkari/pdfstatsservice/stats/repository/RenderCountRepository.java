package com.github.zukkari.pdfstatsservice.stats.repository;

import com.github.zukkari.pdfstatsservice.stats.data.RenderCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RenderCountRepository extends JpaRepository<RenderCount, Long> {
}
