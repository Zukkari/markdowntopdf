package com.github.zukkari.pdfstatsservice.stats.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigInteger;

@Entity
public class RenderCount {

    @Id
    private Long id = 1L;

    @Column(precision = 10, scale = 2)
    private BigInteger count;

    public BigInteger getCount() {
        return count;
    }

    public RenderCount increment() {
        return RenderCount.of(this.count.add(BigInteger.ONE));
    }

    public static RenderCount of(BigInteger count) {
        RenderCount renderCount = new RenderCount();
        renderCount.count = count;
        return renderCount;
    }
}
