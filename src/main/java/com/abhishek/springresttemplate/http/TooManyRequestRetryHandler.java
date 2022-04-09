package com.abhishek.springresttemplate.http;

import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultServiceUnavailableRetryStrategy;
import org.apache.http.protocol.HttpContext;

public class TooManyRequestRetryHandler extends DefaultServiceUnavailableRetryStrategy {

    private static final int TOO_MANY_REQUESTS = 429;
    private static int MAX_RETRIES = 0;

    public TooManyRequestRetryHandler(final int maxRetries, final int retryInterval) {
        super(maxRetries, retryInterval);
        MAX_RETRIES = maxRetries;
    }

    @Override
    public boolean retryRequest(HttpResponse response, int executionCount, HttpContext context) {
        boolean retryRequest = super.retryRequest(response, executionCount, context);
        if (retryRequest || (executionCount <= MAX_RETRIES && (response.getStatusLine().getStatusCode() == TOO_MANY_REQUESTS
                || response.getStatusLine().getStatusCode() == org.apache.http.HttpStatus.SC_SERVICE_UNAVAILABLE))) {
            retryRequest = true;
        }
        return retryRequest;
    }
}
