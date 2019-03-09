package com.github.zukkari.pdffrontend.controller;

import com.github.zukkari.pdffrontend.service.RenderCountService;
import com.github.zukkari.pdffrontend.service.RenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;

@Controller
@RequestMapping("/")
public class PdfFormController {
    private static final Logger log = LoggerFactory.getLogger(PdfFormController.class);

    private RenderCountService countService;
    private RenderService renderService;

    @Autowired
    public PdfFormController(RenderCountService countService, RenderService renderService) {
        this.countService = countService;
        this.renderService = renderService;
    }

    @GetMapping
    public String index(Model model) throws URISyntaxException {
        model.addAttribute("pdfCount", countService.getCount());
        return "index.html";
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Resource> render(@RequestParam("file") MultipartFile multipartFile) throws IOException, URISyntaxException {
        byte[] content = multipartFile.getBytes();
        String originalFilename = multipartFile.getOriginalFilename();

        log.info("Started pdf rendering for file '{}' with content size {} bytes", originalFilename, content.length);
        long start = System.currentTimeMillis();

        Resource render = renderService.render(content, originalFilename);

        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.setContentType(MediaType.APPLICATION_PDF);
        respHeaders.setContentLength(render.contentLength());
        respHeaders.setContentDispositionFormData("attachment", render.getFilename());

        long end = System.currentTimeMillis();
        log.info("Rendering finished in {} ms", end - start);

        return new ResponseEntity<>(render, respHeaders, HttpStatus.OK);
    }
}
