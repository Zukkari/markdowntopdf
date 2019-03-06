package com.github.zukkari.stats.client;

import com.github.zukkari.stats.client.exception.ConnectionFailure;
import com.github.zukkari.stats.client.exception.InvalidRequestException;

import java.net.HttpURLConnection;
import java.net.URL;

public class StatisticsClient {
    public static final String STATS_HEADER = "stats-auth";

    private final String host;
    private final String token;

    public StatisticsClient(String host, String token) {
        this.host = host;
        this.token = token;
    }

    public void increment() {
        HttpURLConnection connect = null;
        try {
            URL url = new URL(host);
            connect = (HttpURLConnection) url.openConnection();
            connect.addRequestProperty(STATS_HEADER, token);
            connect.setRequestMethod("POST");

            int resp = connect.getResponseCode();
            if (resp != HttpURLConnection.HTTP_OK) {
                throw new InvalidRequestException(resp);
            }
        } catch (Exception e) {
            throw new ConnectionFailure(e);
        } finally {
            if (connect != null) {
                connect.disconnect();
            }
        }
    }
}
