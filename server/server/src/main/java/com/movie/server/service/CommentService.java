package com.movie.server.service;


import com.movie.server.exception.DatabaseOperationException;
import com.movie.server.exception.NotFoundException;
import com.movie.server.model.Comment;
import com.movie.server.model.Media;
import com.movie.server.model.User;
import com.movie.server.repository.CommentRepository;
import com.movie.server.repository.MediaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final MediaRepository mediaRepository;

    private final AuthenticationService authenticationService;

    public CommentService(CommentRepository commentRepository, MediaRepository mediaRepository, AuthenticationService authenticationService) {
        this.commentRepository = commentRepository;
        this.mediaRepository = mediaRepository;
        this.authenticationService = authenticationService;
    }

    @Transactional
    public void addCommentToMedia(String imdbId, String token, String comment) {
        if (imdbId == null) {
            throw new IllegalArgumentException("Id must not be null");
        }
        if (token == null) {
            throw new IllegalArgumentException("Token must not be null");
        }
        if (comment == null) {
            throw new IllegalArgumentException("Comment must not be null");
        }
        Media media = mediaRepository.findByImdbId(imdbId);
        if (media == null) {
            throw new NotFoundException("Media does not exist");
        }
        User user = authenticationService.getUser(token);
        if (user == null) {
            throw new NotFoundException("User does not exist");
        }
        try {
            List<Comment> comments = media.getComments();
            Comment newComment = new Comment(null, user.getUsername(), comment, media);
            comments.add(newComment);
            media.setComments(comments);
            mediaRepository.save(media);
        } catch (Exception e) {
            throw new DatabaseOperationException("Error occurred when adding comment to media", e);
        }

    }

    public List<Comment> getAllCommentsFromMedia(String imdbId) {
        if (imdbId == null) {
            throw new IllegalArgumentException("ID must not be null");
        }
        try {
            var medias = mediaRepository.findByImdbId(imdbId);
            if (medias != null) {
                return medias.getComments();
            }
            return null;
        } catch (Exception e) {
            throw new DatabaseOperationException("Error occurred when getting comments from media", e);
        }

    }

    public Long getNumberOfComments(String imdbId) {
        if (imdbId == null) {
            throw new IllegalArgumentException("ID must not be null");
        }
        try {
            return commentRepository.countByMediaImdbId(imdbId);
        } catch (Exception e) {
            throw new DatabaseOperationException("Error occurred when getting the number of comments from media", e);
        }
    }
}
