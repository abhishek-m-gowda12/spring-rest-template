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

    @Bean
    @Qualifier("amsServiceRestTemplate")
    public RestTemplate amsServiceRestTemplate(MeterRegistry meterRegistry,
                                               @Value("${rest.template.connection.timeOut}") int connectionTimeOut,
                                               @Value("${rest.template.request.timeOut}") int requestTimeOut,
                                               @Value("${rest.template.socket.timeOut}") int socketTimeOut) {
        HttpClientConfig httpClientConfig = new HttpClientConfig.Builder()
                .setConnectionTimeOut(connectionTimeOut)
                .setRequestTimeOut(requestTimeOut)
                .setSocketTimeOut(socketTimeOut)
                .setMaxTotal(60)
                .setMaxPerRoute(30)
                .setRequestInterceptors(List.of(new RequestResponseLoggingInterceptor()))
                .build();

        return RestTemplateFactory.restTemplate(httpClientConfig, meterRegistry, Boolean.FALSE);
    }

    @Bean
    @Qualifier("criteoRetailMediaRestTemplate")
    public RestTemplate criteoRetailMediaRestTemplate(MeterRegistry meterRegistry,
                                                      OAuth2Config oAuth2Config,
                                                      CachingUntilExpiryCriteoOAuth2TokenService cachingUntilExpiryCriteoOAuth2TokenService,
                                                      @Value("${criteo.retailmedia.connection.timeOutInMillis}") int connectionTimeOut,
                                                      @Value("${criteo.retailmedia.request.timeOutInMillis}") int requestTimeOut,
                                                      @Value("${criteo.retailmedia.socket.timeOutInMillis}") int socketTimeOut,
                                                      @Value("${criteo.retailmedia.retry.interval.timeOutInMillis}") int retryInterval,
                                                      @Value("${criteo.retailmedia.retry.count}") int retryCount) {
        HttpClientConfig httpClientConfig = new HttpClientConfig.Builder()
                .setConnectionTimeOut(connectionTimeOut)
                .setRequestTimeOut(requestTimeOut)
                .setSocketTimeOut(socketTimeOut)
                .setRetryHandler(new DefaultHttpRequestRetryHandler(retryCount, true))
                .setRequestInterceptors(List.of(
                        new RequestResponseLoggingInterceptor(),
                        new CriteoOAuth2TokenInterceptor(cachingUntilExpiryCriteoOAuth2TokenService, oAuth2Config)))
                .build();

        return RestTemplateFactory.restTemplate(httpClientConfig, meterRegistry, Boolean.TRUE);
    }
}
