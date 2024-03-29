package com.example.gateway.route;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    private static final String REGISTER_URL = "/auth-service/register";
    private static final String LOGIN_URL = "/auth-service/login";
    private static final String VALIDATE_TOKEN_URL = "/auth-service/validate";
    private static final String VALIDATE_USERNAME_URL = "/user-service/check";

    private static final List<String> openApiEndpoints = List.of(REGISTER_URL,
            LOGIN_URL, VALIDATE_TOKEN_URL, VALIDATE_USERNAME_URL);

    private static final Predicate<ServerHttpRequest> mustBeSecured = httpServletRequest ->
            openApiEndpoints.stream().noneMatch(uri ->
                    httpServletRequest.getURI().getPath().contains(uri));

    public Predicate<ServerHttpRequest> getSecurityPredicate() {
        return mustBeSecured;
    }
}
