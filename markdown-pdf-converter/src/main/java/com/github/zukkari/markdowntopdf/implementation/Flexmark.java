package com.github.zukkari.markdowntopdf.implementation;

import com.github.zukkari.markdowntopdf.MarkdownConverter;
import com.github.zukkari.markdowntopdf.PageFinalizer;
import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.options.MutableDataSet;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Arrays;

public class Flexmark extends MarkdownConverter {
    private static final Charset charset = Charset.forName("UTF-8");
    private final Parser parser;
    private final HtmlRenderer renderer;
    private final PageFinalizer finalizer;

    public Flexmark(PageFinalizer finalizer) {
        MutableDataSet options = getOptions();

        this.parser = Parser.builder(options).build();
        this.renderer = HtmlRenderer.builder(options).build();
        this.finalizer = finalizer;
    }

    private MutableDataSet getOptions() {
        MutableDataSet options = new MutableDataSet();
        options.set(Parser.EXTENSIONS, Arrays.asList(TablesExtension.create(), StrikethroughExtension.create()));

        return options;
    }

    @Override
    protected InputStream render(InputStream os) throws IOException {
        Reader reader = new InputStreamReader(os, charset);

        Node document = parser.parseReader(reader);
        String html = finalizer.finalizePage(renderer.render(document));

        return new ByteArrayInputStream(html.getBytes(charset));
    }
}
