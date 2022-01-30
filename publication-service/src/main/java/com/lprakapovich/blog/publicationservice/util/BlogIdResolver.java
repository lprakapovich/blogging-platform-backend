package com.lprakapovich.blog.publicationservice.util;

import com.lprakapovich.blog.publicationservice.security.SecurityContextHolderSubjectResolver;

public class BlogIdResolver {

    private BlogIdResolver() {}

    public static String resolveBlogIdFromPrincipal() {
        return SecurityContextHolderSubjectResolver.getPrincipalSubject();
    }
}
