package com.blogApp.services.impl;

import com.blogApp.entities.Post;
import com.blogApp.exceptions.ResourceNotFoundException;
import com.blogApp.payload.PostDto;
import com.blogApp.payload.PostResponse;
import com.blogApp.repository.PostRepository;
import com.blogApp.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepo;

    private ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepo, ModelMapper modelMapper) {
        this.postRepo = postRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

        Post post = mapToPost(postDto);
        Post save = postRepo.save(post);
        PostDto dto = mapToPostDto(post);
        return dto;
    }

    @Override
    public PostResponse findAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> postss = postRepo.findAll(pageable);
        List<Post> posts = postss.getContent();

//        List<Post> posts = postRepo.findAll(pageable);
        List<PostDto> listPosts = posts.stream().map(post -> mapToPostDto(post)).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(listPosts);
        postResponse.setPageNo(postss.getNumber());
        postResponse.setPageSize(postss.getSize());
        postResponse.setTotalPages(postss.getTotalPages());
        postResponse.setTotalElement(postss.getTotalElements());
        postResponse.setLast(postss.isLast());
        return postResponse  ;
    }

    @Override
    public PostDto findPostById(Long id) {
        Post byId = postRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("post", "id", id)
        );
//        Post post = byId.get();
        PostDto postDto = mapToPostDto(byId);

        return postDto;
    }

    @Override
    public PostDto updateThePost(PostDto postDto, Long id) {
        Post post = postRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("post", "id", id)
        );
        post.setName(postDto.getName());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());
        Post save = postRepo.save(post);
        PostDto dto = mapToPostDto(post);
        return dto;
    }

    @Override
    public PostDto deleteById(Long id) {
        postRepo.deleteById(id);
        return null;
    }


    public Post mapToPost(PostDto postDto){
        Post post = modelMapper.map(postDto, Post.class);

//        Post post = new Post();
//        post.setName(postDto.getName());
//        post.setContent(postDto.getContent());
//        post.setDescription(postDto.getDescription());
        return post;
    }

    public PostDto mapToPostDto(Post post){
        PostDto postDto = modelMapper.map(post, PostDto.class);
//        PostDto postDto = new PostDto();
//        postDto.setId(post.getId());
//        postDto.setName(post.getName());
//        postDto.setContent(post.getContent());
//        postDto.setDescription(post.getDescription());
        return postDto;


    }
}
