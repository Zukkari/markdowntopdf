package com.github.zukkari.pdfstatsservice;

import com.github.zukkari.pdfstatsservice.controller.RenderCountController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PdfRenderCountServiceApplicationTests {

    @Autowired
    private RenderCountController renderCountController;

    @Test
    public void contextLoads() {
        assertThat(renderCountController).isNotNull();
    }
}
