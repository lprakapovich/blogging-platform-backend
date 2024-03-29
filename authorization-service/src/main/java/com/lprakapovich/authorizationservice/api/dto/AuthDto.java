package com.lprakapovich.authorizationservice.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class AuthDto {

    @NotBlank(message = "Token cannot be empty")
    private String token;
}
