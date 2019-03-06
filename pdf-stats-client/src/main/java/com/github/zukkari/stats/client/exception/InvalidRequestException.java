package com.github.zukkari.stats.client.exception;

public class InvalidRequestException extends RuntimeException {

    public InvalidRequestException(int code) {
        super(String.format("Response code is not OK: %d", code));
    }
}
