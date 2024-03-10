package com.movie.server.service;

import com.movie.server.exception.NotFoundException;
import com.movie.server.model.Comment;
import com.movie.server.model.Media;
import com.movie.server.model.User;
import com.movie.server.model.enums.Role;
import com.movie.server.repository.CommentRepository;
import com.movie.server.repository.MediaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @Mock
    CommentRepository commentRepository;
    @Mock
    MediaRepository mediaRepository;
    @Mock
    AuthenticationService authenticationService;

    @InjectMocks
    CommentService commentService;

    private User user;
    private Media media;
    private String token;

    private String comment;

    @BeforeEach
    void setup() {
        user = new User(1L, "lucas",  "senha", List.of(), Role.USER);

        media = new Media(1L,
                "The Shawshank Redemption",
                "tt0111161",
                "Over the course of several years, two convicts form a friendship, seeking consolation and, eventually, redemption through basic compassion.",
                "https://m.media-amazon.com/images/M/MV5BNDE3ODcxYzMtY2YzZC00NmNlLWJiNDMtZDViZWM2MzIxZDYwXkEyXkFqcGdeQXVyNjAwNDUxODI@._V1_.jpg",
                "16",
                List.of("Drama"),
                8520,
                9.3F,
                1,
                "movie",
                1994,
                0,
                new ArrayList<>(),
                new ArrayList<>());

        token = "token";

        comment = "foo";
    }

    @Test
    @DisplayName("Should successfully add a new comment to a media")
    void addCommentToMedia() {
        when(mediaRepository.findByImdbId(media.getImdbId())).thenReturn(media);
        when(authenticationService.getUser(token)).thenReturn(user);

        commentService.addCommentToMedia(media.getImdbId(), token, comment);

        verify(mediaRepository).save(media);

        Assertions.assertAll(() -> {
            Assertions.assertFalse(media.getComments().isEmpty());
            Assertions.assertEquals(media.getComments().stream().map(Comment::getUserComment).toList().get(0), comment);
        });
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when adding comment to media with null imdbId")
    void addCommentToMediaExceptionIllegalArgumentException1() {
        Exception thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            commentService.addCommentToMedia(null, token, comment);
        });

        Assertions.assertEquals("Id must not be null", thrown.getMessage());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when adding comment to media with null token")
    void addCommentToMediaExceptionIllegalArgumentException2() {
        Exception thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            commentService.addCommentToMedia(media.getImdbId(), null, comment);
        });

        Assertions.assertEquals("Token must not be null", thrown.getMessage());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when adding comment to media with null comment")
    void addCommentToMediaExceptionIllegalArgumentException3() {
        Exception thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            commentService.addCommentToMedia(media.getImdbId(), token, null);
        });
        Assertions.assertEquals("Comment must not be null", thrown.getMessage());
    }

    @Test
    @DisplayName("Should throw NotFoundException when adding comment to media that doesn't exist")
    void addCommentToMediaExceptionNotFoundExceptionException1() {

        when(mediaRepository.findByImdbId(media.getImdbId())).thenReturn(null);

        Exception thrown = Assertions.assertThrows(NotFoundException.class, () -> {
            commentService.addCommentToMedia(media.getImdbId(), token, comment);
        });
        Assertions.assertEquals("Media does not exist", thrown.getMessage());
    }

    @Test
    @DisplayName("Should throw NotFoundException when adding comment to media with user that doesn't exist")
    void addCommentToMediaExceptionNotFoundExceptionException2() {

        when(mediaRepository.findByImdbId(media.getImdbId())).thenReturn(media);
        when(authenticationService.getUser(token)).thenReturn(null);

        Exception thrown = Assertions.assertThrows(NotFoundException.class, () -> {
            commentService.addCommentToMedia(media.getImdbId(), token, comment);
        });
        Assertions.assertEquals("User does not exist", thrown.getMessage());
    }



    @Test
    @DisplayName("Should successfully return all the comments from a media")
    void getAllCommentsFromMedia() {
        Comment comment_ = new Comment(1L, user.getUsername(), comment, media);
        media.setComments(List.of(comment_));

        when(mediaRepository.findByImdbId(media.getImdbId())).thenReturn(media);

        commentService.getAllCommentsFromMedia(media.getImdbId());

        Assertions.assertTrue(media.getComments().contains(comment_));

    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when getting all the comments from a media with null imdbId ")
    void getAllCommentsFromMediaExceptionIllegalArgumentException1() {

        Exception thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            commentService.getAllCommentsFromMedia(null);
        });
        Assertions.assertEquals("ID must not be null", thrown.getMessage());
    }

}
