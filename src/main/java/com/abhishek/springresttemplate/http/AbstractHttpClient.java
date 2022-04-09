package com.abhishek.springresttemplate.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

/**
 * Class that manages the interactions with {@code RestTemplate}.
 * This class takes care of constructing the URI from query or path parameters.
 */
public abstract class AbstractHttpClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractHttpClient.class);

    private final RestTemplate restTemplate;

    protected AbstractHttpClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    protected <REQ, RES> ResponseEntity<RES> get(HttpServiceContext context, Class<RES> responseClass) {
        URI fullUrl = getServiceURI(context);
        HttpEntity<REQ> httpEntity = new HttpEntity<>(context.getHttpHeaders());
        return restTemplate.exchange(fullUrl, HttpMethod.GET, httpEntity, responseClass);
    }

    protected <REQ, RES> ResponseEntity<RES> get(HttpServiceContext context, ParameterizedTypeReference<RES> typeReference) {
        URI fullUrl = getServiceURI(context);
        HttpEntity<REQ> httpEntity = new HttpEntity<>(context.getHttpHeaders());
        return restTemplate.exchange(fullUrl, HttpMethod.GET, httpEntity, typeReference);
    }

    protected <REQ, RES> ResponseEntity<RES> post(HttpServiceContext context, REQ request, Class<RES> responseClass) {
        URI fullUrl = getServiceURI(context);
        HttpEntity<REQ> httpEntity = new HttpEntity<>(request, context.getHttpHeaders());
        return restTemplate.exchange(fullUrl, HttpMethod.POST, httpEntity, responseClass);
    }

    protected <REQ, RES> ResponseEntity<RES> post(HttpServiceContext context, REQ request, ParameterizedTypeReference<RES> typeReference) {
        URI fullUrl = getServiceURI(context);
        HttpEntity<REQ> httpEntity = new HttpEntity<>(request, context.getHttpHeaders());
        return restTemplate.exchange(fullUrl, HttpMethod.POST, httpEntity, typeReference);
    }

    protected <REQ, RES> ResponseEntity<RES> put(HttpServiceContext context, REQ request, Class<RES> responseClass) {
        URI fullUrl = getServiceURI(context);
        HttpEntity<REQ> httpEntity = new HttpEntity<>(request, context.getHttpHeaders());
        return restTemplate.exchange(fullUrl, HttpMethod.PUT, httpEntity, responseClass);
    }

    protected <REQ, RES> ResponseEntity<RES> delete(HttpServiceContext context, REQ request, Class<RES> responseClass) {
        URI fullUrl = getServiceURI(context);
        HttpEntity<REQ> httpEntity = new HttpEntity<>(request, context.getHttpHeaders());
        return restTemplate.exchange(fullUrl, HttpMethod.DELETE, httpEntity, responseClass);
    }

    private URI getServiceURI(HttpServiceContext context) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(context.getBaseUri());

        //Set path after baseUri
        ofNullable(context.getPath()).ifPresent(builder::path);

        //Set Query params if present
        ofNullable(context.getQueryParams())
                .ifPresent(queryParams -> builder.queryParams(multiValueMapOfQueryParams(queryParams)));

        //buildAndExpand if path params are present else just build
        return ofNullable(context.getPathParams())
                .map(builder::buildAndExpand)
                .orElse(builder.build())
                .toUri();
    }

    private MultiValueMap<String, String> multiValueMapOfQueryParams(Map<String, String> queryParams) {
        return new LinkedMultiValueMap<>(queryParams.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> Collections.singletonList(entry.getValue()))));
    }
}
