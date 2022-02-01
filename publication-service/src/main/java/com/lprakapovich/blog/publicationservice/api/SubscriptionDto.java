package com.lprakapovich.blog.publicationservice.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Data
class SubscriptionDto {

    @NotBlank(message = "Subscription blog id cannot be blank")
    private String subscriptionTargetBlogId;
}