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

    public Media createMedia(Media media) {
        List<User> user = media.getUsers();
        for (User u : user) {
            if ( userRepository.findUserByUsername(u.getUsername()).get() == u ) {
                return mediaRepository.save(media);
            }
        }
        throw new ObjectNotFoundException("Media must have a user to be created with", media);
    }

}
