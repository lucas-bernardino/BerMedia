package com.movie.server.repository;

import com.movie.server.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Long countByMediaImdbId(String imdbID);

}
