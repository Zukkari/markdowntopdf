package com.github.zukkari.pdfstatsservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.zukkari.pdfstatsservice.stats.data.RenderCountDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigInteger;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RenderCountControllerTests {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;


    @Before
    public void setUp() {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    @Test
    public void getStatsTest() throws Exception {
        MvcResult result = mvc.perform(get("/v1/stats/"))
                .andExpect(status().isOk())
                .andReturn();

        RenderCountDTO dto = mapper.readValue(result.getResponse().getContentAsByteArray(), RenderCountDTO.class);

        // Verify that dto has been passed
        assertThat(dto).isNotNull();

        // Verify that result is present even if nothing is in the db
        assertThat(dto.getCount()).isNotNull();

        // Verify that count is zero with freshly initialized app
        assertThat(dto.getCount()).isEqualTo(BigInteger.ZERO);
    }

    @Test
    public void postWorks() throws Exception {
        mvc.perform(post("/v1/stats"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void incrementWorks() throws Exception {
        MvcResult first = mvc.perform(get("/v1/stats"))
                .andExpect(status().isOk())
                .andReturn();

        BigInteger initial = mapper.readValue(first.getResponse().getContentAsByteArray(), RenderCountDTO.class).getCount();

        mvc.perform(post("/v1/stats"))
                .andExpect(status().isOk())
                .andReturn();

        MvcResult second = mvc.perform(get("/v1/stats"))
                .andExpect(status().isOk())
                .andReturn();

        BigInteger postIncrement = mapper.readValue(second.getResponse().getContentAsByteArray(), RenderCountDTO.class).getCount();

        // Verify that incrementing increased the value
        assertThat(postIncrement).isEqualTo(initial.add(BigInteger.ONE));
    }
}
