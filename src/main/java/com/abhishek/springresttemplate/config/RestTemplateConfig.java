package com.abhishek.springresttemplate.config;

import com.intentwise.criteo.config.OAuth2Config;
import com.intentwise.criteo.httpclient.RestTemplateFactory;
import com.intentwise.criteo.httpclient.interceptor.CriteoOAuth2TokenInterceptor;
import com.intentwise.criteo.httpclient.interceptor.RequestResponseLoggingInterceptor;
import com.intentwise.criteo.httpclient.service.CachingUntilExpiryCriteoOAuth2TokenService;
import io.micrometer.core.instrument.MeterRegistry;
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
    @Qualifier("oauth2RestTemplate")
    public RestTemplate criteoOAuth2RestTemplate(MeterRegistry meterRegistry,
                                                 @Value("${oauth.token.connection.timeOutInMillis}") int connectionTimeOut,
                                                 @Value("${oauth.token.request.timeOutInMillis}") int requestTimeOut,
                                                 @Value("${oauth.token.socket.timeOutInMillis}") int socketTimeOut,
                                                 @Value("${oauth.token.retry.interval.timeOutInMillis}") int retryInterval,
                                                 @Value("${oauth.token.retry.count}") int retryCount) {
        HttpClientConfig httpClientConfig = new HttpClientConfig.Builder()
                .setConnectionTimeOut(connectionTimeOut)
                .setRequestTimeOut(requestTimeOut)
                .setSocketTimeOut(socketTimeOut)
                .setRetryHandler(new DefaultHttpRequestRetryHandler(retryCount, true))
                .setRequestInterceptors(List.of(new RequestResponseLoggingInterceptor()))
                .build();

        return RestTemplateFactory.restTemplate(httpClientConfig, meterRegistry, Boolean.FALSE);
    }
}
