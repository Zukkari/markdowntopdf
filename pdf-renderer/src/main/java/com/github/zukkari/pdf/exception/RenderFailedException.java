package com.github.zukkari.pdf.exception;

public class RenderFailedException extends RuntimeException {

    public RenderFailedException(Exception e) {
        super(e);
    }

    public RenderFailedException(String message) {
        super(message);
    }
}
