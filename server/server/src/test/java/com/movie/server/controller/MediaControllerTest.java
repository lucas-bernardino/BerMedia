package com.movie.server.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movie.server.model.Media;
import com.movie.server.model.User;
import com.movie.server.model.dto.IdUserMediaDto;
import com.movie.server.model.enums.Role;
import com.movie.server.service.AuthenticationService;
import com.movie.server.service.MediaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@ExtendWith(MockitoExtension.class)
public class MediaControllerTest {

    @Mock
    MediaService mediaService;

    @Mock
    AuthenticationService authenticationService;

    @InjectMocks
    MediaController mediaController;
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private Media media;
    private String mediaJson;

    private User user;
    private IdUserMediaDto idUserMediaDto;

    private String token;

    @BeforeEach
    void setup() throws JsonProcessingException {
        mockMvc = MockMvcBuilders.standaloneSetup(mediaController).alwaysDo(print()).build();
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
                List.of());
        idUserMediaDto = new IdUserMediaDto(media.getImdbId(), 1L);
        token = "token";
        mediaJson = objectMapper.writeValueAsString(List.of(media));

        // Need to replace the 'users' field with nothing, since the response body in the controller doesn't contain this field to avoid infinite loop.
        mediaJson = mediaJson.replace(",\"users\":[]", "");


    }

    @Test
    @DisplayName("Should create a media successfully")
    void createMediaSuccessfully() throws Exception {

        String mediaBody = objectMapper.writeValueAsString(media);

        doNothing().when(mediaService).createMedia(media);

        mockMvc.perform(post("/media").
                contentType(MediaType.APPLICATION_JSON).
                content(mediaBody)
        ).andExpect(status().isOk());

        verify(mediaService).createMedia(media);
        verifyNoMoreInteractions(mediaService);

    }

    @Test
    @DisplayName("Should return a list of medias successfully")
    void getAllMediasByUsernameSuccessfully() throws Exception {
        when(authenticationService.getUser(token)).thenReturn(user);
        when(mediaService.getAllMediasByUsername(user.getUsername())).thenReturn(List.of(media));

        mockMvc.perform(get("/media").
                contentType(MediaType.APPLICATION_JSON).
                accept(MediaType.APPLICATION_JSON).
                header("Authorization", token)).andExpect(status().isOk()).andExpect(content().json(mediaJson));

        verify(authenticationService).getUser(token);
        verify(mediaService).getAllMediasByUsername(user.getUsername());

        verifyNoMoreInteractions(authenticationService);
        verifyNoMoreInteractions(mediaService);
    }


}
