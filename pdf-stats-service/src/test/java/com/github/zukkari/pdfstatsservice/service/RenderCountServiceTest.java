package com.github.zukkari.pdfstatsservice.service;

import com.github.zukkari.pdfstatsservice.stats.data.RenderCount;
import com.github.zukkari.pdfstatsservice.stats.repository.RenderCountRepository;
import com.github.zukkari.pdfstatsservice.stats.service.RenderCountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.math.BigInteger;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
public class RenderCountServiceTest {

    @Autowired
    private RenderCountRepository repository;

    @Autowired
    private RenderCountService service;

    @Test
    public void zero() {
        BigInteger count = service.getCount();

        // Verify that count is zero when not incremented
        assertThat(count).isEqualTo(BigInteger.ZERO);

        // Verify that value in repo does not exist
        Optional<RenderCount> entity = repository.findById(1L);
        assertThat(entity).isEmpty();
    }

    @Test
    public void increment() {
        // Verify that value in repo does not exist
        Optional<RenderCount> entity = repository.findById(1L);
        assertThat(entity).isEmpty();

        BigInteger initial = service.getCount();
        service.increment();


        BigInteger increment = service.getCount();

        // Verify that incremented only by one
        assertThat(increment).isEqualTo(initial.add(BigInteger.ONE));

        // Verify that value now exists in repo
        Optional<RenderCount> postIncrement = repository.findById(1L);
        assertThat(postIncrement).isNotEmpty();
    }
}
