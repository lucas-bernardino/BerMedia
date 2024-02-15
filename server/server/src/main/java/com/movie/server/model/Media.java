package com.movie.server.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "media")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
//@JsonIgnoreProperties(value = { "users" })
public class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonView({View.Default.class, View.Test.class})
    private Long id;

    @Column(name = "title")
    @JsonView({View.Default.class, View.Test.class})
    private String title;

    @Column(name = "imdbId", unique = true)
    @JsonView({View.Default.class, View.Test.class})
    private String imdbId;

    @Column(name = "plot")
    @JsonView({View.Default.class, View.Test.class})
    private String plot;

    @Column(name = "pictureUrl")
    @JsonView({View.Default.class, View.Test.class})
    private String pictureUrl;

    @Column(name = "certificate")
    @JsonView({View.Default.class, View.Test.class})
    private String certificate;

    @ElementCollection
    @CollectionTable(name = "media_genre", joinColumns = @JoinColumn(name = "media_id"))
    @Column(name = "genre")
    @JsonView({View.Default.class, View.Test.class})
    private List<String> genre;

    @Column(name = "length")
    @JsonView({View.Default.class, View.Test.class})
    private Integer length;

    @Column(name = "score")
    @JsonView({View.Default.class, View.Test.class})
    private Float score;

    @Column(name = "rank")
    @JsonView({View.Default.class, View.Test.class})
    private Integer rank;

    @Column(name = "titleType")
    @JsonView({View.Default.class, View.Test.class})
    private String titleType;

    @Column(name = "yearStart")
    @JsonView({View.Default.class, View.Test.class})
    private Integer yearStart;

    @Column(name = "yearEnd")
    @JsonView({View.Default.class, View.Test.class})
    private Integer yearEnd;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "user_media", joinColumns = @JoinColumn(name = "media_id"), inverseJoinColumns = @JoinColumn(name = "users_id"))
    @JsonView({View.Test.class})
    private List<User> users;

    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER, mappedBy = "media")
    @JsonView({View.Default.class, View.Test.class})
    private List<Comment> comments = new ArrayList<>();

}
