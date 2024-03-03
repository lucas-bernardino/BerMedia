package com.movie.server.service;

import com.movie.server.exception.DatabaseOperationException;
import com.movie.server.exception.NotFoundException;
import com.movie.server.model.Media;
import com.movie.server.model.User;
import com.movie.server.repository.MediaRepository;
import com.movie.server.repository.UserRepository;
import org.springframework.http.ResponseEntity;
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
        } catch (IllegalArgumentException e) {
            throw e;
        }
        catch (Exception e) {
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
        } catch (NotFoundException e) {
            throw e;
        }
        catch (Exception e) {
            throw new DatabaseOperationException("Error saving media to the database", e);
        }
    }

    public List<Media> getAllMedias() {
        return mediaRepository.findAll();
    }

    @Transactional
    public void addUserToMedia(String imdbId, Long userId) {

        if (imdbId == null) {
            throw new IllegalArgumentException("Missing imdbId field");
        }

        if (userId == null) {
            throw new IllegalArgumentException("Missing userId field");
        }
        try {
            Media media = mediaRepository.findByImdbId(imdbId);
            if (media == null) {
                throw new NotFoundException(String.format("Media with id (%s) not found.", imdbId));
            }
            Optional<User> newUser = userService.getUserById(userId);
            if (newUser.isEmpty()) {
                throw new NotFoundException(String.format("User with id (%s) not found.", userId));
            }
            List<User> mediaUsers = media.getUsers();
            mediaUsers.add(newUser.get());
            media.setUsers(mediaUsers);
            mediaRepository.save(media);
        } catch (IllegalArgumentException | NotFoundException e) {
            throw e;
        }
        catch (Exception e){
            throw new DatabaseOperationException("Error adding user to media", e);
        }

    }

    public Media getMediaByImdbId(String id) {
        try {
            if (id == null) {
                throw new IllegalArgumentException("ID must not be null");
            }
            Media media = mediaRepository.findByImdbId(id);
            if (media == null) {
                throw new NotFoundException("Media not found");
            }
            return media;
        }
        catch (IllegalArgumentException | NotFoundException e) {
            throw e;
        }
        catch (Exception e) {
            throw new DatabaseOperationException("Error finding media by IMDB id", e);
        }
    }

    @Transactional
    public void deleteMediaByImdbId(String imdbId, String token) {
        if (imdbId == null) {
            throw new IllegalArgumentException("ID must not be null");
        }
        if (token == null) {
            throw new IllegalArgumentException("Token must not be null");
        }
        try {
            Media media = mediaRepository.findByImdbId(imdbId);
            if (media == null) {
                throw new NotFoundException("Media to be deleted does not exist");
            }
            User user = authenticationService.getUser(token);
            if (user == null) {
                throw new NotFoundException("User does not exist");
            }
            List<Media> userMedias = user.getMedias();
            if (!userMedias.contains(media)) {
                throw new NotFoundException("User is not linked to this media");
            }
            userMedias.remove(media);
            user.setMedias(userMedias);

        } catch (IllegalArgumentException | NotFoundException e) {
            throw e;
        }
        catch (Exception e) {
            throw new DatabaseOperationException("Error deleting media by id", e);
        }
    }

}
