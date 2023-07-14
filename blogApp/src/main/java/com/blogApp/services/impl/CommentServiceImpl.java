package com.blogApp.services.impl;

import com.blogApp.entities.Comment;
import com.blogApp.entities.Post;
import com.blogApp.exceptions.ResourceNotFoundException;
import com.blogApp.payload.CommentDto;
import com.blogApp.repository.CommentRepository;
import com.blogApp.repository.PostRepository;
import com.blogApp.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;

    private PostRepository postRepository;

    private ModelMapper modelMapper;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CommentDto createComment(Long postId, CommentDto commentDto) {

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("post", "id", postId)
        );

        Comment comment = mapToComment(commentDto);
        comment.setPost(post);
        Comment savedComment = commentRepository.save(comment);
        CommentDto dto = mapToCommentDto(comment);


        return dto;
    }

    @Override
    public List<CommentDto> getAllCommentsByPostID(Long postId) {
        List<Comment> allCommentsByPostId = commentRepository.findAllCommentsByPostId(postId);
        List<CommentDto> collectedCooment = allCommentsByPostId.stream().map(comment -> mapToCommentDto(comment)).collect(Collectors.toList());
        return collectedCooment;
    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("post", "id", postId)
        );
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("comment", "id", commentId)
        );
        CommentDto commentDto = mapToCommentDto(comment);
        return commentDto;
    }

    @Override
    public CommentDto updateCommentById(Long postId, Long commentId, CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("post", "id", postId)
        );
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("comment", "id", commentId)
        );
        comment.setBody(commentDto.getBody());
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        Comment updatedComment = commentRepository.save(comment);
        CommentDto updatedCommentDto = mapToCommentDto(comment);

        return updatedCommentDto;
    }

    public Comment mapToComment(CommentDto commentDto){
        Comment comment = modelMapper.map(commentDto, Comment.class);
//        Comment comment = new Comment();
//        comment.setName(commentDto.getName());
//        comment.setEmail(commentDto.getEmail());
//        comment.setBody(commentDto.getBody());
        return comment;
    }

    public CommentDto mapToCommentDto(Comment comment){
        CommentDto commentDto = modelMapper.map(comment, CommentDto.class);
//        CommentDto commentDto = new CommentDto();
//        commentDto.setId(comment.getId());
//        commentDto.setName(comment.getName());
//        commentDto.setEmail(comment.getEmail());
//        commentDto.setBody(comment.getBody());
        return commentDto;
    }

}
