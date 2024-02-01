package com.movie.server.model;

import jakarta.persistence.*;
import lombok.*;

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
public class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "plot")
    private String plot;

    @Column(name = "pictureUrl")
    private String pictureUrl;

    @Column(name = "certificate")
    private String certificate;

    @ElementCollection
    @CollectionTable(name = "media_genre", joinColumns = @JoinColumn(name = "media_id"))
    @Column(name = "genre")
    private List<String> genre;

    @ElementCollection
    @CollectionTable(name = "media_length", joinColumns = @JoinColumn(name = "media_id"))
    @MapKeyColumn(name = "unit")
    @Column(name = "value")
    private Map<String, Integer> length = new HashMap<>();

    @Column(name = "rank")
    private Float rank;

    @Column(name = "ranking")
    private Float ranking;

    @Column(name = "titleType")
    private String titleType;

    @Column(name = "yearStart")
    private Integer yearStart;

    @Column(name = "yearEnd")
    private Integer yearEnd;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_media", joinColumns = @JoinColumn(name = "media_id"), inverseJoinColumns = @JoinColumn(name = "users_id"))
    private List<User> users;


}
