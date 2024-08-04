package com.g1RssApi.dtos;

import jakarta.validation.constraints.NotBlank;

/**
 * @author Carlos Santos
 */
public record AuthenticationUserDTO(
        @NotBlank(message = "login cannot be blank") String login,
        @NotBlank(message = "password cannot be blank") String password
) {
}
