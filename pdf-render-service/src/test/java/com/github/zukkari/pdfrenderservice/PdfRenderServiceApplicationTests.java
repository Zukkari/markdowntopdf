package com.github.zukkari.pdfrenderservice;

import com.github.zukkari.pdfrenderservice.controller.MarkdownToPdf;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PdfRenderServiceApplicationTests {

    @Autowired
    private MarkdownToPdf controller;

    @Test
    public void contextLoads() {
        assertThat(controller).isNotNull();
    }
}
