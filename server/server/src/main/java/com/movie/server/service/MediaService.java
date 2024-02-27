package com.movie.server.service;

import com.movie.server.exception.DatabaseOperationException;
import com.movie.server.exception.NotFoundException;
import com.movie.server.model.Media;
import com.movie.server.model.User;
import com.movie.server.repository.MediaRepository;
import com.movie.server.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MediaService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final MediaRepository mediaRepository;

    private final AuthenticationService authenticationService;
    public MediaService(UserRepository userRepository, UserService userService, MediaRepository mediaRepository, AuthenticationService authenticationService) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.mediaRepository = mediaRepository;
        this.authenticationService = authenticationService;
    }

    public List<Media> getAllMediasByUsername(String username) {
        try {
            if (username == null) {
                throw new IllegalArgumentException("Username argument must not be null");
            }
            Optional<User> user = userService.getUserByUsername(username);
            if (user.isPresent()) {
                return user.get().getMedias();
            }
            return new ArrayList<>();
        } catch (Exception e) {
            throw new DatabaseOperationException("Error getting all medias by username", e);
        }

    }

    @Transactional
    public void createMedia(Media media) {
        try {
            List<User> user = media.getUsers();
            int numberOfElementsThatExists = user.stream().filter(u -> userRepository.existsById(u.getId())).toList().size();
            if (user.size() != numberOfElementsThatExists) {
                throw new NotFoundException("Media contains users that do not exist");
            }
            mediaRepository.save(media);
        } catch (Exception e) {
            throw new DatabaseOperationException("Error saving media to the database", e);
        }
    }

    public List<Media> getAllMedias() {
        return mediaRepository.findAll();
    }

    public void addUserToMedia(String imdbId, Long userId) {

        if (imdbId == null) {
            throw new IllegalArgumentException("Missing imdbId field");
        }

        if (userId == null) {
            throw new IllegalArgumentException("Missing userId field");
        }

        Media media = mediaRepository.findByImdbId(imdbId);
        if (media == null) {
            throw new NotFoundException(String.format("Media with id (%s) not found.", imdbId));
        }
        Optional<User> newUser = userService.getUserById(userId);
        if (newUser.isEmpty()) {
            throw new NotFoundException(String.format("User with id (%s) not found.", imdbId));
        };
        List<User> mediaUsers = media.getUsers();
        mediaUsers.add(newUser.get());
        media.setUsers(mediaUsers);
        mediaRepository.save(media);
    }

    public Media getMediaByImdbId(String id) {
        return mediaRepository.findByImdbId(id);
    }

    @Transactional
    public void deleteMediaByImdbId(String imdbId, String token) {
        Media media = mediaRepository.findByImdbId(imdbId);
        User user = authenticationService.getUser(token);
        List<Media> userMedias = user.getMedias();
        userMedias.remove(media);
        user.setMedias(userMedias);
    }


}
