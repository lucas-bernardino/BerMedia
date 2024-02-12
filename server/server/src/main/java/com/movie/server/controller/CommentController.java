package com.movie.server.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.movie.server.model.Comment;
import com.movie.server.model.View;
import com.movie.server.service.CommentService;
import com.movie.server.service.MediaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    public CommentController(MediaService mediaService, CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{imdbId}")
    public ResponseEntity<Void> addCommentToMedia(@PathVariable String imdbId, @RequestHeader("Authorization") String token, @RequestBody HashMap<String, String> comment) {
        commentService.addCommentToMedia(imdbId, token, comment.get("userComment"));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{imdbId}")
    @JsonView(View.Default.class)
    public ResponseEntity<List<Comment>> getAllComentsFromMedia(@PathVariable String imdbId) {
        List<Comment> comments = commentService.getAllCommentsFromMedia(imdbId);
        if (comments == null) {
            return ResponseEntity.ok().body(new ArrayList<>());
        }
        return ResponseEntity.ok().body(comments);
    }

    @GetMapping("/total/{imdbId}")
    @JsonView(View.Default.class)
    public ResponseEntity<Long> getNumberOfCommentsFromMedia(@PathVariable String imdbId) {
        Long numberOfComments = commentService.getNumberOfComments(imdbId);
        return ResponseEntity.ok().body(numberOfComments);
    }

}
