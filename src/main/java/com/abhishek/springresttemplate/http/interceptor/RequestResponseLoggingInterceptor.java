package com.abhishek.springresttemplate.http.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of {@code ClientHttpRequestInterceptor} to log the request and the response for external HTTP calls.
 */
public class RequestResponseLoggingInterceptor implements ClientHttpRequestInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestResponseLoggingInterceptor.class);

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        logHttpRequest(request, body);

        ClientHttpResponse response = execution.execute(request, body);

        logHttpResponse(request, response);
        return response;
    }

    private void logHttpRequest(HttpRequest request, byte[] body) {
        Map<String, Object> requestLog = new HashMap<>();
        requestLog.put("uri", request.getURI());
        requestLog.put("httpMethod", request.getMethodValue());
        if (body != null && body.length > 0) {
            requestLog.put("requestBody", new String(body, StandardCharsets.UTF_8));
        }
        LOGGER.info("operation = logHttpRequest {}", requestLog);
    }

    private void logHttpResponse(HttpRequest request, ClientHttpResponse response) {
        Map<String, Object> responseLog = new HashMap<>();
        try {
            responseLog.put("uri", request.getURI());
            responseLog.put("httpMethod", request.getMethodValue());
            responseLog.put("statusCode", response.getRawStatusCode());
            responseLog.put("responseBody", new String(StreamUtils.copyToByteArray(response.getBody())));
            LOGGER.info("operation = logResponse {}", responseLog);
        } catch (Exception ignored) {
            //ignored intentionally
        }
    }
}
