package dev.prashant.jdbcclient.controller;

import dev.prashant.jdbcclient.model.Post;
import dev.prashant.jdbcclient.service.PostService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(@Qualifier("jdbcClientPostService") PostService postService) {
        this.postService = postService;
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    List<Post> findAll() {
        return postService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    Optional<Post> findById(@PathVariable String id) {
        return postService.findById(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    void create(@RequestBody Post post) {
        postService.create(post);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    void update(@RequestBody Post post, @PathVariable String id) {
        postService.update(post, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    void delete(@PathVariable String id) {
        postService.delete(id);
    }
}
