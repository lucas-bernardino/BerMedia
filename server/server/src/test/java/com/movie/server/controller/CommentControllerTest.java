package com.movie.server.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movie.server.model.Comment;
import com.movie.server.model.Media;
import com.movie.server.model.User;
import com.movie.server.model.enums.Role;
import com.movie.server.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@ExtendWith(MockitoExtension.class)
public class CommentControllerTest {

    @Mock
    CommentService commentService;
    @InjectMocks
    CommentController commentController;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private User user;
    private Media media;
    private String token;
    private HashMap<String, String> comment = new HashMap<>();
    private String commentBody;

    @BeforeEach
    void setup() throws JsonProcessingException {
        mockMvc = MockMvcBuilders.standaloneSetup(commentController).alwaysDo(print()).build();

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
                new ArrayList<>()
        );

        token = "token";

        comment = new HashMap<>();
        comment.put("userComment", "foo");

        commentBody = objectMapper.writeValueAsString(comment);

    }

    @Test
    @DisplayName("Should successfully add a comment to a media")
    void addCommentToMedia() throws Exception {

        doNothing().when(commentService).addCommentToMedia(media.getImdbId(), token, comment.get("userComment"));

        mockMvc.perform(post("/comment/tt0111161").
                contentType(MediaType.APPLICATION_JSON).
                content(commentBody).
                header("Authorization", token)
        ).andExpect(status().isOk());

        verify(commentService).addCommentToMedia(media.getImdbId(), token, comment.get("userComment"));
        verifyNoMoreInteractions(commentService);

    }

    @Test
    @DisplayName("Should successfully get all comments from a media")
    void getAllCommentsFromMedia() throws Exception {

        Comment comment1 = new Comment(1L, user.getUsername(), comment.get("userComment"), (Instant) null);
        Comment comment2 = new Comment(null, user.getUsername(), comment.get("userComment"), (Instant) null);

        media.setComments(List.of(comment1, comment2));

        String commentsBody = objectMapper.writeValueAsString(List.of(comment1, comment2));

        commentsBody = commentsBody.replace("\"media\":null,", "");

        when(commentService.getAllCommentsFromMedia(media.getImdbId())).thenReturn(List.of(comment1, comment2));

        mockMvc.perform(get("/comment/tt0111161").
                contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andExpect(content().json(commentsBody));

        verify(commentService).getAllCommentsFromMedia(media.getImdbId());
        verifyNoMoreInteractions(commentService);

    }

}
