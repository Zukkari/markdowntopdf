package com.github.zukkari.pdf.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public final class Streams {

    private Streams() {
    }

    public static void copy(InputStream in, OutputStream out) throws IOException  {
        byte[] buff = new byte[1048576];
        int read;
        while ((read = in.read(buff)) != -1) {
            out.write(buff, 0, read);
        }
    }
}
