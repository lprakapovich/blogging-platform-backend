package com.lprakapovich.blog.publicationservice.api.dto;

import com.lprakapovich.blog.publicationservice.model.Blog.BlogId;
import com.lprakapovich.blog.publicationservice.model.Category;
import com.lprakapovich.blog.publicationservice.model.Subscription;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogViewDto {

    private BlogId id;
    private String displayName;
    private String description;
    private List<Category> categories;
    private List<Subscription> subscriptions;
    private List<Subscription> subscribers;
    private int numberOfPublications;
}
