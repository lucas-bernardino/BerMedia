package com.movie.server.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.movie.server.model.Media;
import com.movie.server.model.View;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.time.ZonedDateTime;

@Entity
@Table(name = "comment")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonView({View.Default.class, View.Test.class})
    private Long id;

    @Column(name = "username")
    @JsonView({View.Default.class, View.Test.class})
    private String username;

    @Column(name = "userComment")
    @JsonView({View.Default.class, View.Test.class})
    private String userComment;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "media_imdb_id", referencedColumnName = "imdbId")
    private Media media;

    public Comment(Long id, String username, String userComment, Media media) {
        this.id = id;
        this.username = username;
        this.userComment = userComment;
        this.media = media;
    }

    public Comment(Long id, String username, String userComment, Instant createdOn) {
        this.id = id;
        this.username = username;
        this.userComment = userComment;
        this.createdOn = createdOn;
    }

    @Column(name = "createdOn")
    @CreationTimestamp
    @JsonView({View.Default.class, View.Test.class})
    private Instant createdOn;

}
