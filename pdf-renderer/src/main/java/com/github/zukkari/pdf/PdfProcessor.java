package com.github.zukkari.pdf;

import java.io.File;
import java.io.InputStream;

public interface PdfProcessor {

    InputStream render(String id, File inputFile);

}
