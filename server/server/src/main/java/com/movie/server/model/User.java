package com.movie.server.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username", unique = true)
    @NotEmpty
    @Size(min = 3, max = 30)
    private String username;

    @Column(name = "password")
    @NotEmpty
    @Size(min = 3, max = 30)
    private String password;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_media", joinColumns = @JoinColumn(name = "users_id"), inverseJoinColumns = @JoinColumn(name = "media_id"))
    private List<Media> medias;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

}
