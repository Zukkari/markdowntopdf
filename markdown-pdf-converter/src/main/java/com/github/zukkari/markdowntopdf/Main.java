package com.github.zukkari.markdowntopdf;

import com.github.zukkari.markdowntopdf.implementation.Flexmark;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        MarkdownConverter md = new Flexmark();
        md.convert(new FileInputStream(new File("/home/zukkari/git/masters/esi/demo/report.md")));
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
