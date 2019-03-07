package com.github.zukkari.pdfstatsservice.stats.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class RenderCount {

    @Id
    private Long id = 1L;

    @Column(precision = 10, scale = 2)
    private BigDecimal count;

    public BigDecimal getCount() {
        return count;
    }

    public RenderCount increment() {
        return RenderCount.of(this.count.add(BigDecimal.ONE));
    }

    public static RenderCount of(BigDecimal count) {
        RenderCount renderCount = new RenderCount();
        renderCount.count = count;
        return renderCount;
    }
}
