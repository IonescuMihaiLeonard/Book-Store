package com.bookstore.gateway.controller;

import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Enumeration;
import java.util.Set;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@RestController
public class GatewayController {

    private static final Set<String> SKIPPED_HEADERS = Set.of(
            "connection",
            "content-length",
            "expect",
            "host",
            "transfer-encoding",
            "upgrade"
    );

    private final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(5))
            .build();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final String authServiceUrl;
    private final String catalogServiceUrl;
    private final String orderServiceUrl;

    public GatewayController(
            @Value("${services.auth-url}") String authServiceUrl,
            @Value("${services.catalog-url}") String catalogServiceUrl,
            @Value("${services.order-url}") String orderServiceUrl
    ) {
        this.authServiceUrl = trimTrailingSlash(authServiceUrl);
        this.catalogServiceUrl = trimTrailingSlash(catalogServiceUrl);
        this.orderServiceUrl = trimTrailingSlash(orderServiceUrl);
    }

    @RequestMapping("/api/v1/**")
    public ResponseEntity<byte[]> proxy(HttpServletRequest servletRequest, @RequestBody(required = false) byte[] body)
            throws IOException, InterruptedException {
        String path = servletRequest.getRequestURI();
        String targetBaseUrl = resolveTargetBaseUrl(path);
        String queryString = servletRequest.getQueryString();
        if (isAdminPath(path)) {
            assertAdmin(servletRequest);
        }
        if (isOrderServicePath(path) && !hasQueryParam(queryString, "userId")) {
            queryString = appendQueryParam(queryString, "userId", resolveAuthenticatedUserId(servletRequest));
        }
        URI targetUri = buildTargetUri(targetBaseUrl, path, queryString);

        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder(targetUri)
                .timeout(Duration.ofSeconds(20));

        copyRequestHeaders(servletRequest, requestBuilder);
        requestBuilder.method(
                servletRequest.getMethod(),
                requestBodyPublisher(servletRequest.getMethod(), body)
        );

        HttpResponse<byte[]> response = httpClient.send(requestBuilder.build(), HttpResponse.BodyHandlers.ofByteArray());
        HttpHeaders responseHeaders = new HttpHeaders();
        response.headers().map().forEach((name, values) -> {
            if (!SKIPPED_HEADERS.contains(name.toLowerCase())) {
                responseHeaders.addAll(name, values);
            }
        });

        return ResponseEntity.status(response.statusCode())
                .headers(responseHeaders)
                .body(response.body());
    }

    private String resolveTargetBaseUrl(String path) {
        if (path.startsWith("/api/v1/auth")) {
            return authServiceUrl;
        }
        if (path.startsWith("/api/v1/admin/users")) {
            return authServiceUrl;
        }
        if (path.startsWith("/api/v1/admin/orders")) {
            return orderServiceUrl;
        }
        if (path.startsWith("/api/v1/admin/addresses")
                || path.startsWith("/api/v1/admin/carts")
                || path.startsWith("/api/v1/admin/cart-items")
                || path.startsWith("/api/v1/admin/order-items")
                || path.startsWith("/api/v1/admin/payments")) {
            return orderServiceUrl;
        }
        if (path.startsWith("/api/v1/admin/books")
                || path.startsWith("/api/v1/admin/authors")
                || path.startsWith("/api/v1/admin/categories")
                || path.startsWith("/api/v1/admin/reviews")) {
            return catalogServiceUrl;
        }
        if (path.startsWith("/api/v1/cart")
                || path.startsWith("/api/v1/address")
                || path.startsWith("/api/v1/orders")) {
            return orderServiceUrl;
        }
        if (path.startsWith("/api/v1/books")) {
            return catalogServiceUrl;
        }
        throw new IllegalArgumentException("No route configured for " + path);
    }

    private boolean isAdminPath(String path) {
        return path.startsWith("/api/v1/admin");
    }

    private boolean isOrderServicePath(String path) {
        return path.startsWith("/api/v1/cart")
                || path.startsWith("/api/v1/address")
                || path.startsWith("/api/v1/orders");
    }

    private String resolveAuthenticatedUserId(HttpServletRequest servletRequest)
            throws IOException, InterruptedException {
        JsonNode user = resolveAuthenticatedUser(servletRequest);
        JsonNode id = user.get("id");
        if (id == null || id.isNull()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authenticated user has no id");
        }

        return id.asText();
    }

    private void assertAdmin(HttpServletRequest servletRequest) throws IOException, InterruptedException {
        JsonNode user = resolveAuthenticatedUser(servletRequest);
        JsonNode role = user.get("role");
        if (role == null || role.isNull() || !"ADMIN".equals(role.asText())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Admin role required");
        }
    }

    private JsonNode resolveAuthenticatedUser(HttpServletRequest servletRequest)
            throws IOException, InterruptedException {
        String authorization = servletRequest.getHeader("Authorization");
        if (authorization == null || authorization.isBlank()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing Authorization header");
        }

        HttpRequest request = HttpRequest.newBuilder(URI.create(authServiceUrl + "/api/v1/auth/me"))
                .timeout(Duration.ofSeconds(10))
                .header("Authorization", authorization)
                .GET()
                .build();

        HttpResponse<byte[]> response = httpClient.send(request, HttpResponse.BodyHandlers.ofByteArray());
        if (response.statusCode() < 200 || response.statusCode() >= 300) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Authorization token");
        }

        return objectMapper.readTree(response.body());
    }

    private URI buildTargetUri(String targetBaseUrl, String path, String queryString) {
        StringBuilder uri = new StringBuilder(targetBaseUrl).append(path);
        if (queryString != null && !queryString.isBlank()) {
            uri.append("?").append(queryString);
        }
        return URI.create(uri.toString());
    }

    private boolean hasQueryParam(String queryString, String paramName) {
        if (queryString == null || queryString.isBlank()) {
            return false;
        }
        for (String param : queryString.split("&")) {
            String key = param.split("=", 2)[0];
            if (paramName.equals(key)) {
                return true;
            }
        }
        return false;
    }

    private String appendQueryParam(String queryString, String name, String value) {
        String encodedName = URLEncoder.encode(name, StandardCharsets.UTF_8);
        String encodedValue = URLEncoder.encode(value, StandardCharsets.UTF_8);
        String param = encodedName + "=" + encodedValue;
        if (queryString == null || queryString.isBlank()) {
            return param;
        }
        return queryString + "&" + param;
    }

    private void copyRequestHeaders(HttpServletRequest servletRequest, HttpRequest.Builder requestBuilder) {
        Enumeration<String> headerNames = servletRequest.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            if (SKIPPED_HEADERS.contains(headerName.toLowerCase())) {
                continue;
            }

            Enumeration<String> headerValues = servletRequest.getHeaders(headerName);
            while (headerValues.hasMoreElements()) {
                requestBuilder.header(headerName, headerValues.nextElement());
            }
        }
    }

    private HttpRequest.BodyPublisher requestBodyPublisher(String method, byte[] body) {
        if (body == null || "GET".equalsIgnoreCase(method) || "DELETE".equalsIgnoreCase(method)) {
            return HttpRequest.BodyPublishers.noBody();
        }
        return HttpRequest.BodyPublishers.ofByteArray(body);
    }

    private String trimTrailingSlash(String value) {
        if (value.endsWith("/")) {
            return value.substring(0, value.length() - 1);
        }
        return value;
    }
}
