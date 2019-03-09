package com.github.zukkari.pdffrontend.controller;

import com.github.zukkari.pdffrontend.integration.RenderCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URISyntaxException;

@Controller
@RequestMapping("/")
public class PdfFormController {

    private RenderCountService countService;

    @Autowired
    public PdfFormController(RenderCountService countService) {
        this.countService = countService;
    }

    @GetMapping
    public String index(Model model) throws URISyntaxException {
        model.addAttribute("pdfCount", countService.getCount());
        return "index.html";
    }

    @PostMapping
    public void render() {

    }
}
