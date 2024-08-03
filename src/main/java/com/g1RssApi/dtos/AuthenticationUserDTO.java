package com.g1RssApi.dtos;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationUserDTO(
        @NotBlank(message = "login cannot be blank") String login,
        @NotBlank(message = "password cannot be blank") String password
) {
}
