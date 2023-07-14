package com.blogApp.controller;

import com.blogApp.constants.FinalConstants;
import com.blogApp.payload.PostDto;
import com.blogApp.payload.PostResponse;
import com.blogApp.services.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto){

        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    @GetMapping
    public PostResponse getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = FinalConstants.Default_PAGE_NO, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = FinalConstants.Default_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = FinalConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = FinalConstants.DEFAULT_SORT_DIR, required = false) String sortDir
            )
    {
        PostResponse allPosts = postService.findAllPosts(pageNo, pageSize, sortBy, sortDir);
        return allPosts;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> findPostById(@PathVariable Long id){
        PostDto postById = postService.findPostById(id);
        return new ResponseEntity<>(postById, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Long id){
        PostDto dto = postService.updateThePost(postDto, id);

        return new ResponseEntity<>( dto, HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<PostDto> deletePostById(@PathVariable Long id){
        PostDto dto = postService.deleteById(id);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
}
