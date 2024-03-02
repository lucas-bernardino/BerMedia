package com.movie.server.service;

import com.movie.server.exception.NotFoundException;
import com.movie.server.model.Media;
import com.movie.server.model.User;
import com.movie.server.model.enums.Role;
import com.movie.server.repository.MediaRepository;
import com.movie.server.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MediaServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserService userService;
    @Mock
    private MediaRepository mediaRepository;
    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    MediaService mediaService;

    @Test
    @DisplayName("Should create a media sucessfully")
    void createMediaSucessfully() {
        User user = new User(1L, "lucas",  "senha", List.of(), Role.USER);
        Media media = new Media(1L,
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
                List.of(user),
                List.of());

        when(userRepository.existsById(1L)).thenReturn(true);
        mediaService.createMedia(media);
        verify(mediaRepository).save(media);
    }

    @Test
    @DisplayName("Should thrown an exception when creating media with user that doesn't exist in the database")
    void createMediaException() {
        User user = new User(1L, "lucas",  "senha", List.of(), Role.USER);

        Media media = new Media(1L,
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
                List.of(user),
                List.of());

        Exception thrown = Assertions.assertThrows(NotFoundException.class, () -> {
            mediaService.createMedia(media);
        });

        Assertions.assertEquals("Media contains users that do not exist", thrown.getMessage());

    }


}
