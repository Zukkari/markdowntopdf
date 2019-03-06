package com.github.zukkari.pdfstatsservice.stats.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class Statistics {

    @Id
    private Long id = 1L;

    @Column(precision = 10, scale = 2)
    private BigDecimal count;

    public BigDecimal getCount() {
        return count;
    }

    public Statistics increment() {
        Statistics s = new Statistics();
        s.id = this.id;
        s.count = this.count == null ? BigDecimal.ZERO : this.count.add(BigDecimal.ONE);

        return s;
    }
}
