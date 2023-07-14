package com.blogApp.controller;

import com.blogApp.payload.CommentDto;
import com.blogApp.services.CommentService;
import com.blogApp.services.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {

    private PostService postService;

    private CommentService commentService;

    public CommentController(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    @PostMapping("post/{postId}/comment")
    public ResponseEntity<CommentDto> createComment (@PathVariable("postId") Long postId, @RequestBody CommentDto commentDto){
        CommentDto comment = commentService.createComment(postId, commentDto);
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    @GetMapping("post/{postId}/comment")
    public List<CommentDto> getAllComments(@PathVariable("postId") Long postId){
        List<CommentDto> allCommentsByPostID = commentService.getAllCommentsByPostID(postId);
        return allCommentsByPostID;
    }

    @GetMapping("post/{postId}/comment/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId){
        CommentDto commentById = commentService.getCommentById(postId, commentId);
        return new ResponseEntity<>(commentById, HttpStatus.OK);
    }

    @PutMapping("post/{postId}/comment/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId, @RequestBody CommentDto commentDto){
        CommentDto updatedCommentDto = commentService.updateCommentById(postId, commentId, commentDto);
        return new ResponseEntity<>(updatedCommentDto, HttpStatus.OK);

    }


}
