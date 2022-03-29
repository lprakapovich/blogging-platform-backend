package com.lprakapovich.blog.publicationservice.api.dto;

import com.lprakapovich.blog.publicationservice.model.Blog;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CategoryDto {

    private long id;

    @NotBlank(message = "Category name cannot be blank")
    private String name;
}
