package com.g1RssApi.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * @author Carlos Santos
 */
public record RegisterUserDTO(
        @NotBlank(message = "name cannot be blank") String name,
        @NotBlank(message = "login cannot be blank") String login,
        @NotBlank(message = "passwork cannot be blank") String password,
        @Email(message = "invalid email") String email,
        String phone,
        String birthdate
) {
}
