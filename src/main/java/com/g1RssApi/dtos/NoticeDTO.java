package com.g1RssApi.dtos;

import com.g1RssApi.models.CategoryModel;
import jakarta.validation.constraints.NotBlank;

import java.util.Date;

/**
 * @author Carlos Santos
 */
public record NoticeDTO(
        @NotBlank(message = "Name cannot be blank") String title,
        @NotBlank(message = "Descrition cannot be blank") String description,
        String imageUrl,
        Date pubDate,
        CategoryModel category
) {
}
