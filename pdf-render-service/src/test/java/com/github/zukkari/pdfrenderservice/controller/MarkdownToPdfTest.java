package com.github.zukkari.pdfrenderservice.controller;

import com.github.zukkari.markdowntopdf.PageFinalizer;
import com.github.zukkari.pdf.PdfProcessor;
import com.github.zukkari.pdfrenderservice.discovery.PdfCountService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MarkdownToPdfTest {

    @MockBean
    private PdfProcessor processor;

    @MockBean
    private PageFinalizer finalizer;

    @MockBean
    private PdfCountService service;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void before() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void generationWorks() throws Exception {
        final String testFileName = "test";

        // Prepare pdf processor mock
        when(processor.render(anyString(), any())).thenReturn(new TestStream());

        // Mock page finalizer so it does not look for style
        when(finalizer.finalizePage(anyString())).thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        MvcResult result = mockMvc.perform(post("/v1/render/" + testFileName + ".md"))
                .andExpect(status().isOk())
                .andReturn();

        String header = result.getResponse().getHeader(HttpHeaders.CONTENT_DISPOSITION);

        // Verify that the header is present
        assertThat(header).isNotNull();

        // Verify that content disposition value contains test file name
        assertThat(header).contains(testFileName + ".pdf");

        // Verify that stream is empty since we posted empty content
        byte[] array = result.getResponse().getContentAsByteArray();
        assertThat(array.length).isEqualTo(0);

        // Verify that incrementation call has been dispatched and dont do any network calls when testing
        doNothing().when(service).incrementPdfCount();

        verify(service, timeout(10000L)).incrementPdfCount();
    }

    class TestStream extends InputStream {

        @Override
        public int read() {
            return -1;
        }
    }
}
