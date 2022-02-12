package com.lprakapovich.blog.publicationservice.service;

import com.lprakapovich.blog.publicationservice.exception.BlogNotFoundException;
import com.lprakapovich.blog.publicationservice.model.Blog;
import com.lprakapovich.blog.publicationservice.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;

    public String createBlog(Blog blog) {
        Blog save = blogRepository.save(blog);
        return save.getId();
    }

    public Blog getById(String id) {
        return blogRepository.findById(id).orElseThrow(BlogNotFoundException::new);
    }

    public List<Blog> getAll() {
        ArrayList<Blog> blogs = new ArrayList<>();
        blogRepository.findAll().forEach(blogs::add);
        return blogs;
    }

    public Blog updateBlog(String id, String name, String description) {
        Blog blog = getById(id);
        blog.setName(name);
        blog.setDescription(description);
        return blogRepository.save(blog);
    }

    public void validateExistence(String id) {
        if (!existsById(id)) {
            throw new BlogNotFoundException();
        }
    }

    public boolean existsById(String id) {
        return blogRepository.existsById(id);
    }
}
