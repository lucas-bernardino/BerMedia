package com.movie.server.model;

import com.fasterxml.jackson.annotation.*;
import com.movie.server.model.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonView({View.Default.class, View.Test.class})
    private Long id;

    @Column(name = "username", unique = true)
    @NotEmpty
    @Size(min = 3)
    @JsonView({View.Default.class, View.Test.class})
    private String username;

    @Column(name = "password")
    @NotEmpty
    @Size(min = 3)
    @JsonView({View.Default.class, View.Test.class})
    private String password;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_media", joinColumns = @JoinColumn(name = "users_id"), inverseJoinColumns = @JoinColumn(name = "media_id"))
    @JsonView({View.Default.class})
    private List<Media> medias;

    @Enumerated(EnumType.STRING)
    private Role role;

    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == Role.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
