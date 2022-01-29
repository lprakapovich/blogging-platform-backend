package com.lprakapovich.authorizationservice.api;

import com.lprakapovich.authorizationservice.api.dto.LoginDto;
import com.lprakapovich.authorizationservice.api.dto.AuthResponse;
import com.lprakapovich.authorizationservice.api.dto.RegisterDto;
import com.lprakapovich.authorizationservice.exception.JwtException;
import com.lprakapovich.authorizationservice.jwt.JwtUtil;
import com.lprakapovich.authorizationservice.service.AuthService;
import io.jsonwebtoken.ClaimJwtException;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

import static com.lprakapovich.authorizationservice.exception.JwtException.Cause.INVALID;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthRestEndpoint {

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginDto request) {
        authService.authenticate(request.getUsername(), request.getPassword());
        String token = jwtUtil.generateToken(request.getUsername());
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping(value = "/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterDto request) {
        URI createdResourceLocation = authService.register(request);
        String token = jwtUtil.generateToken(request.getUsername());
        return ResponseEntity.created(createdResourceLocation).body(new AuthResponse(token));
    }

    @PostMapping("/validate")
    ResponseEntity<String> validateToken(@RequestParam String token) {
        // TODO handle builtin exceptions thrown during claims resolution
        if (!jwtUtil.isTokenValid(token)) {
            throw new JwtException(INVALID);
        }
        Claims userClaims = jwtUtil.getAllClaimsFromToken(token);
        return ResponseEntity.ok(userClaims.getSubject());
    }
}