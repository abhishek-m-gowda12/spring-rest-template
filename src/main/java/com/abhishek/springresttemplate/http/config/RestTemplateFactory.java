package com.abhishek.springresttemplate.http.config;

import com.abhishek.springresttemplate.http.CustomizedResponseErrorHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static java.util.Optional.ofNullable;

/**
 * A Factory class to create instances of {@code RestTemplate} per HTTP endpoint or route.
 * This factory wraps {@code CloseableHttpClient} as the request factory.
 * <p>
 * The factory method also manages setting metrics customizers and interceptors for request and response logging
 * for the {@code RestTemplate} instance.
 */
public class RestTemplateFactory {

    public static RestTemplate restTemplate(HttpClientConfig httpClientConfig, boolean isCustomResponseHandlerRequired) {

        RestTemplateBuilder builder = new RestTemplateBuilder()
                .requestFactory(() -> clientHttpRequestFactory(httpClientConfig));

        if (isCustomResponseHandlerRequired) {
            builder = builder.errorHandler(new CustomizedResponseErrorHandler());
        }

        RestTemplate restTemplate = builder.build();
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM));
        restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);
        restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
        ofNullable(httpClientConfig.getRequestInterceptors()).ifPresent(restTemplate::setInterceptors);
        return restTemplate;
    }

    /**
     * Need to wrap in {@code BufferingClientHttpRequestFactory} to allow for logging request and response,
     * without which the underlying streams will be closed before mapping to response class.
     */
    private static BufferingClientHttpRequestFactory clientHttpRequestFactory(HttpClientConfig httpClientConfig) {
        return new BufferingClientHttpRequestFactory(new HttpComponentsClientHttpRequestFactory(getHttpClientInstance(httpClientConfig)));
    }

    private static CloseableHttpClient getHttpClientInstance(HttpClientConfig httpClientConfig) {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(httpClientConfig.getConnectionTimeOut())
                .setConnectionRequestTimeout(httpClientConfig.getRequestTimeOut())
                .setSocketTimeout(httpClientConfig.getSocketTimeOut()).build();

        PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();
        poolingHttpClientConnectionManager.setMaxTotal(httpClientConfig.getMaxTotal());
        poolingHttpClientConnectionManager.setDefaultMaxPerRoute(httpClientConfig.getMaxPerRoute());

        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create()
                .disableContentCompression()
                .setConnectionManager(poolingHttpClientConnectionManager)
                .setDefaultRequestConfig(requestConfig)
                .setConnectionTimeToLive(httpClientConfig.getConnectionTTLinMinutes(), TimeUnit.MINUTES)
                .evictExpiredConnections()
                .evictIdleConnections(httpClientConfig.getEvictIdleConnectionInSeconds(), TimeUnit.SECONDS);

        ofNullable(httpClientConfig.getUnavailableRetryStrategy()).ifPresent(httpClientBuilder::setServiceUnavailableRetryStrategy);
        ofNullable(httpClientConfig.getRetryHandler()).ifPresent(httpClientBuilder::setRetryHandler);

        return httpClientBuilder.build();
    }
}
