package com.abhishek.springresttemplate.http.config;

import com.abhishek.springresttemplate.http.interceptor.RequestResponseLoggingInterceptor;
import com.abhishek.springresttemplate.http.TooManyRequestRetryHandler;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Configuration
public class RestTemplateConfig {

    @Bean
    @Qualifier("demoRestTemplate")
    public RestTemplate getRestTemplate(
            @Value("${oauth.token.connection.timeOutInMillis}") int connectionTimeOut,
            @Value("${oauth.token.request.timeOutInMillis}") int requestTimeOut,
            @Value("${oauth.token.socket.timeOutInMillis}") int socketTimeOut,
            @Value("${oauth.token.retry.interval.timeOutInMillis}") int retryInterval,
            @Value("${oauth.token.retry.count}") int retryCount) {
        HttpClientConfig httpClientConfig = HttpClientConfig.builder()
                .connectionTimeOut(connectionTimeOut)
                .requestTimeOut(requestTimeOut)
                .socketTimeOut(socketTimeOut)
                .unavailableRetryStrategy(new TooManyRequestRetryHandler(retryCount, retryInterval))
                .retryHandler(new DefaultHttpRequestRetryHandler(retryCount, true))
                .requestInterceptors(List.of(new RequestResponseLoggingInterceptor()))
                .build();

        return RestTemplateFactory.restTemplate(httpClientConfig, Boolean.FALSE);
    }
}
