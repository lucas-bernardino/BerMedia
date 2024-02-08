package com.movie.server.service;

import com.movie.server.model.Media;
import com.movie.server.model.User;
import com.movie.server.repository.MediaRepository;
import com.movie.server.repository.UserRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MediaService {

    private final UserRepository userRepository;
    private final UserService userService;

    private final MediaRepository mediaRepository;
    public MediaService(UserRepository userRepository, UserService userService, MediaRepository mediaRepository) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.mediaRepository = mediaRepository;
    }

    public List<Media> getAllMediasByUsername(String username) {
        Optional<User> user = userService.getUserByUsername(username);
        if (user.isPresent()) {
            return user.get().getMedias();
        }
        return new ArrayList<>();
    }


    // pegar todos os usuarios do media -> pegar o ID de cada usuario -> comparar se existe no repostorio. Se todos existirem, faz a operacao. Caso contrario, exception.
    public Media createMedia(Media media) {
        List<User> user = media.getUsers();
        int numberOfElementsThatExists = user.stream().filter(u -> userRepository.existsById(u.getId())).toList().size();
        if (user.size() != numberOfElementsThatExists) {
            throw new ObjectNotFoundException("Media contains users that do not exist", media);
        }
        return mediaRepository.save(media);
    }

    public List<Media> getAllMedias() {
        return mediaRepository.findAll();
    }

    public void addUserToMedia(String imdbId, Long userId) {
        Media media = mediaRepository.findByImdbId(imdbId);
        if (media == null) {
            throw new ObjectNotFoundException(Optional.of("Media not found by id: "), imdbId);
        }
        Optional<User> newUser = userService.getUserById(userId);
        if (newUser.isEmpty()) {
            throw new ObjectNotFoundException(Optional.of("User not found by id: "), String.valueOf(userId));
        };
        List<User> mediaUsers = media.getUsers();
        mediaUsers.add(newUser.get());
        media.setUsers(mediaUsers);
        mediaRepository.save(media);
    }

    public Media getMediaByImdbId(String id) {
        return mediaRepository.findByImdbId(id);
    }
}
