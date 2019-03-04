package com.github.zukkari.pdf;

public final class ChromeOptions {

    private ChromeOptions() {
    }

    public static final String HEADLESS = "--headless";
    public static final String DISABLE_GPU = "--disable-gpu";
    public static final String DISABLE_INFOBARS = "disable-infobars";
    public static final String DEBUGGING_PORT = "--remote-debugging-port=9222";
    public static final String ENABLE_LOGGING = "--enable-logging";
    public static final String VERBOSITY_1 = "--v=1";

    private static final String PRINT_TO_PDF = "--print-to-pdf=%s.pdf";


    public static String printToPdf(String arg) {
        return String.format(PRINT_TO_PDF, arg);
    }
}
