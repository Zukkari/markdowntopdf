package com.github.zukkari.stats.client.exception;

public class ConnectionFailure extends RuntimeException {

    public ConnectionFailure(Exception e) {
        super(e);
    }
}
