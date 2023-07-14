package com.blogApp.services;

import com.blogApp.payload.CommentDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {

CommentDto createComment(Long postId, CommentDto commentDto);

    List<CommentDto> getAllCommentsByPostID(Long postId);

    CommentDto getCommentById(Long postId, Long commentId);

    CommentDto updateCommentById(Long postId, Long commentId, CommentDto commentDto);
}
