package com.abhishek.springresttemplate.http.config;

import lombok.Builder;
import lombok.Getter;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.ServiceUnavailableRetryStrategy;
import org.springframework.http.client.ClientHttpRequestInterceptor;

import java.util.List;

@Getter
@Builder(toBuilder = true)
public class HttpClientConfig {
    private static final int DEFAULT_MAX_TOTAL = 60;
    private static final int DEFAULT_PER_ROUTE = 30;
    private static final int DEFAULT_CONNECTION_TTL_IN_MINUTES = 60;
    private static final int DEFAULT_EVICT_IDLE_CONNECTION_IN_SECONDS = 60;

    private final int connectionTimeOut;
    private final int requestTimeOut;
    private final int socketTimeOut;
    private final int maxTotal;
    private final int maxPerRoute;
    private final int connectionTTLinMinutes;
    private final int evictIdleConnectionInSeconds;
    private final ServiceUnavailableRetryStrategy unavailableRetryStrategy;
    private final HttpRequestRetryHandler retryHandler;
    private final List<ClientHttpRequestInterceptor> requestInterceptors;


    public int getMaxTotal() {
        return maxTotal > 0 ? maxTotal : DEFAULT_MAX_TOTAL;
    }

    public int getMaxPerRoute() {
        return maxPerRoute > 0 ? maxPerRoute : DEFAULT_PER_ROUTE;
    }

    public int getConnectionTTLinMinutes() {
        return connectionTTLinMinutes > 0 ? connectionTTLinMinutes : DEFAULT_CONNECTION_TTL_IN_MINUTES;
    }

    public int getEvictIdleConnectionInSeconds() {
        return evictIdleConnectionInSeconds > 0 ? evictIdleConnectionInSeconds : DEFAULT_EVICT_IDLE_CONNECTION_IN_SECONDS;
    }
}
