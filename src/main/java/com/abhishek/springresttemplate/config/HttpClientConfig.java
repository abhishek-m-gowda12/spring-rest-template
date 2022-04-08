package com.abhishek.springresttemplate.config;

import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.ServiceUnavailableRetryStrategy;
import org.springframework.http.client.ClientHttpRequestInterceptor;

import java.util.List;

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

    public HttpClientConfig(Builder builder) {
        this.connectionTimeOut = builder.connectionTimeOut;;
        this.requestTimeOut = builder.requestTimeOut;
        this.socketTimeOut = builder.socketTimeOut;
        this.maxTotal = (builder.maxTotal > 0 ? builder.maxTotal : DEFAULT_MAX_TOTAL);
        this.maxPerRoute = (builder.maxPerRoute > 0 ? builder.maxPerRoute : DEFAULT_PER_ROUTE);
        this.connectionTTLinMinutes = (builder.connectionTTLinMinutes > 0 ? builder.connectionTTLinMinutes : DEFAULT_CONNECTION_TTL_IN_MINUTES);
        this.evictIdleConnectionInSeconds = (builder.evictIdleConnectionInSeconds > 0 ? builder.evictIdleConnectionInSeconds : DEFAULT_EVICT_IDLE_CONNECTION_IN_SECONDS);
        this.unavailableRetryStrategy = builder.unavailableRetryStrategy;
        this.retryHandler = builder.retryHandler;
        this.requestInterceptors = builder.requestInterceptors;
    }

    public int getConnectionTimeOut() {
        return connectionTimeOut;
    }

    public int getRequestTimeOut() {
        return requestTimeOut;
    }

    public int getSocketTimeOut() {
        return socketTimeOut;
    }

    public int getMaxTotal() {
        return maxTotal;
    }

    public int getMaxPerRoute() {
        return maxPerRoute;
    }

    public int getConnectionTTLinMinutes() {
        return connectionTTLinMinutes;
    }

    public int getEvictIdleConnectionInSeconds() {
        return evictIdleConnectionInSeconds;
    }

    public ServiceUnavailableRetryStrategy getUnavailableRetryStrategy() {
        return unavailableRetryStrategy;
    }

    public HttpRequestRetryHandler getRetryHandler() {
        return retryHandler;
    }

    public List<ClientHttpRequestInterceptor> getRequestInterceptors() {
        return requestInterceptors;
    }

    public static class Builder {
        private int connectionTimeOut;
        private int requestTimeOut;
        private int socketTimeOut;
        private int maxTotal;
        private int maxPerRoute;
        private int connectionTTLinMinutes;
        private int evictIdleConnectionInSeconds;

        private ServiceUnavailableRetryStrategy unavailableRetryStrategy;
        private HttpRequestRetryHandler retryHandler;
        private List<ClientHttpRequestInterceptor> requestInterceptors;

        public Builder setConnectionTimeOut(int connectionTimeOut) {
            this.connectionTimeOut = connectionTimeOut;
            return this;
        }

        public Builder setRequestTimeOut(int requestTimeOut) {
            this.requestTimeOut = requestTimeOut;
            return this;
        }

        public Builder setSocketTimeOut(int socketTimeOut) {
            this.socketTimeOut = socketTimeOut;
            return this;
        }

        public Builder setMaxTotal(int maxTotal) {
            this.maxTotal = maxTotal;
            return this;
        }

        public Builder setMaxPerRoute(int maxPerRoute) {
            this.maxPerRoute = maxPerRoute;
            return this;
        }

        public Builder setConnectionTTLinMinutes(int connectionTTLinMinutes) {
            this.connectionTTLinMinutes = connectionTTLinMinutes;
            return this;
        }

        public Builder setEvictIdleConnectionInSeconds(int evictIdleConnectionInSeconds) {
            this.evictIdleConnectionInSeconds = evictIdleConnectionInSeconds;
            return this;
        }

        public Builder setUnavailableRetryStrategy(ServiceUnavailableRetryStrategy unavailableRetryStrategy) {
            this.unavailableRetryStrategy = unavailableRetryStrategy;
            return this;
        }

        public Builder setRetryHandler(HttpRequestRetryHandler retryHandler) {
            this.retryHandler = retryHandler;
            return this;
        }

        public Builder setRequestInterceptors(List<ClientHttpRequestInterceptor> requestInterceptors) {
            this.requestInterceptors = requestInterceptors;
            return this;
        }

        public HttpClientConfig build() {
            return new HttpClientConfig(this);
        }
    }
}
