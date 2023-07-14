package com.blogApp.services;

import com.blogApp.payload.PostDto;
import com.blogApp.payload.PostResponse;

public interface PostService {
    PostDto createPost(PostDto postDto);

    PostResponse findAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto findPostById(Long id);

    PostDto updateThePost(PostDto postDto, Long id);

    PostDto deleteById(Long id);

//    Void deleteById(Long id);
}
