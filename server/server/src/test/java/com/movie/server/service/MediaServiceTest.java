package com.movie.server.service;

import com.movie.server.exception.NotFoundException;
import com.movie.server.model.Media;
import com.movie.server.model.User;
import com.movie.server.model.enums.Role;
import com.movie.server.repository.MediaRepository;
import com.movie.server.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    void createMediaSuccessfully() {
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
                List.of(),
                List.of());

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

    @Test
    @DisplayName("Should get medias by username sucessfully")
    void getAllMediasByUsernameSuccessfully() {
        User user = new User(1L, "lucas",  "senha", List.of(), Role.USER);
        Media media1 = new Media(1L,
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

        Media media2 = new Media(2L,
                "Breaking Bad",
                "tt0903747",
                "A chemistry teacher diagnosed with inoperable lung cancer turns to manufacturing and selling methamphetamine with a former student in order to secure his family's future.",
                "https://m.media-amazon.com/images/M/MV5BYmQ4YWMxYjUtNjZmYi00MDQ1LWFjMjMtNjA5ZDdiYjdiODU5XkEyXkFqcGdeQXVyMTMzNDExODE5._V1_.jpg",
                "16",
                List.of("Crime", "Drama", "Thriller"),
                2700,
                9.5F,
                1,
                "tvSeries",
                2008,
                2013,
                List.of(user),
                List.of());

        user.setMedias(List.of(media1, media2));

        when(userService.getUserByUsername(user.getUsername())).thenReturn(Optional.of(user));

        List<Media> medias = mediaService.getAllMediasByUsername(user.getUsername());

        Assertions.assertAll(() -> {
            Assertions.assertTrue(medias.contains(media1));
            Assertions.assertTrue(medias.contains(media2));
        });

    }

    @Test
    @DisplayName("Should thrown exception when getting medias with null username")
    void getAllMediasByUsernameException1() {
        User user = new User(1L, null,  "senha", List.of(), Role.USER);

        Exception thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            mediaService.getAllMediasByUsername(user.getUsername());
        });

        Assertions.assertEquals("Username argument must not be null", thrown.getMessage());

    }

    @Test
    @DisplayName("Should succesfully add user to a media")
    void addUserToMediaSuccessfully() {
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
                new ArrayList<>(),
                List.of());

        when(mediaRepository.findByImdbId(media.getImdbId())).thenReturn(media);
        when(userService.getUserById(user.getId())).thenReturn(Optional.of(user));

        mediaService.addUserToMedia(media.getImdbId(), user.getId());

        verify(mediaRepository).save(media);

        Assertions.assertTrue(media.getUsers().contains(user));

    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when adding user to a media with null imdbId field")
    void addUserToMediaExceptionIllegalArgumentException1() {

        Exception thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            mediaService.addUserToMedia(null, 1L);
        });

        Assertions.assertEquals("Missing imdbId field", thrown.getMessage());

    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when adding user to a media with null userId field")
    void addUserToMediaExceptionIllegalArgumentException2() {

        Exception thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            mediaService.addUserToMedia("123abc", null);
        });

        Assertions.assertEquals("Missing userId field", thrown.getMessage());

    }

    @Test
    @DisplayName("Should throw NotFoundException when adding user to a media with null media")
    void addUserToMediaExceptionNotFoundException1() {

        when(mediaRepository.findByImdbId("123abc")).thenReturn(null);

        Exception thrown = Assertions.assertThrows(NotFoundException.class, () -> {
            mediaService.addUserToMedia("123abc", 1L);
        });

        Assertions.assertEquals(String.format("Media with id (%s) not found.", "123abc"), thrown.getMessage());

    }

    @Test
    @DisplayName("Should throw NotFoundException when adding user to a media with null media")
    void addUserToMediaExceptionNotFoundException2() {


        when(mediaRepository.findByImdbId("123abc")).thenReturn(new Media());

        Optional<User> user = Optional.of(new User());

        when(userService.getUserById(1L)).thenReturn(Optional.<User>empty());

        Exception thrown = Assertions.assertThrows(NotFoundException.class, () -> {
            mediaService.addUserToMedia("123abc", 1L);
        });

        Assertions.assertEquals(String.format("User with id (%s) not found.", 1L), thrown.getMessage());

    }

    @Test
    @DisplayName("Should successfully get media by ImdbId")
    void getMediaByImdbIdSuccessfully() {

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
                List.of(),
                List.of());


        when(mediaRepository.findByImdbId(media.getImdbId())).thenReturn(media);

        var mediaReturned = mediaService.getMediaByImdbId(media.getImdbId());

        Assertions.assertEquals(media, mediaReturned);

    }


    @Test
    @DisplayName("Should throw IllegalArgumentException when getting media with ImdbId null")
    void getMediaByImdbIdExceptionIllegalArgumentException() {

        Exception thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            mediaService.getMediaByImdbId(null);
        });

        Assertions.assertEquals("ID must not be null", thrown.getMessage());

    }

    @Test
    @DisplayName("Should throw NotFoundException when getting media that doesn't exist")
    void getMediaByImdbIdExceptionNotFoundException() {

        Exception thrown = Assertions.assertThrows(NotFoundException.class, () -> {
            mediaService.getMediaByImdbId("");
        });

        Assertions.assertEquals("Media not found", thrown.getMessage());

    }
}
