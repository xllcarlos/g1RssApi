package com.g1RssApi.dtos;

import jakarta.validation.constraints.NotBlank;

public record CategoryDTO(
        @NotBlank(message = "Name cannot be blank") String name,
        @NotBlank(message = "Link cannot be blank") String link
) {
}
