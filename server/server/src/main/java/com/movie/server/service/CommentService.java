package com.movie.server.service;


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
        Media media = mediaRepository.findByImdbId(imdbId);
        User user = authenticationService.getUser(token);
        List<Comment> comments = media.getComments();
        Comment newComment = new Comment(null, user.getUsername(), comment, media);
        comments.add(newComment);
        media.setComments(comments);
        mediaRepository.save(media);
    }

    public List<Comment> getAllCommentsFromMedia(String imdbId) {
        var medias = mediaRepository.findByImdbId(imdbId);
        if (medias != null) {
            return medias.getComments();
        }
        return null;
    }

    public Long getNumberOfComments(String imdbId) {
        return commentRepository.countByMediaImdbId(imdbId);
    }
}
