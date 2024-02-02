package com.movie.server.repository;

import com.movie.server.model.Media;
import com.movie.server.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MediaRepository extends JpaRepository<Media, Long> {

    Media findByImdbId(String imdbId);

}
