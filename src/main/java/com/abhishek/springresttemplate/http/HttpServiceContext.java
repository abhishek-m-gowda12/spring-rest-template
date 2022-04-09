package com.abhishek.springresttemplate.http;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpHeaders;

import java.util.Map;

/**
 * Class to manage the context like baseUri, path, query params, path params and headers.
 * Use the Builder methods to create context object per invocation.
 */

@Getter
@Builder(toBuilder = true)
public class HttpServiceContext {
    private final String baseUri;
    private final String path;
    private final Map<String, String> queryParams;
    private final Map<String, String> pathParams;
    private final HttpHeaders httpHeaders;
}
