package com.lprakapovich.blog.publicationservice.api.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CategoryDto {

    @NotBlank(message = "Category name cannot be blank")
    private String name;
}
