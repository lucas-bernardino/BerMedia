package com.movie.server.service;

import com.movie.server.auth.TokenService;
import com.movie.server.model.User;
import com.movie.server.model.dto.AuthenticationDTO;
import com.movie.server.model.dto.LoginResponseDto;
import com.movie.server.model.dto.RegisterDto;
import com.movie.server.model.enums.Role;
import com.movie.server.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
public class AuthenticationService implements UserDetailsService {

    private final ApplicationContext applicationContext;
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private AuthenticationManager authenticationManager;
    public AuthenticationService(ApplicationContext applicationContext, UserRepository userRepository, TokenService tokenService) {
        this.applicationContext = applicationContext;
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public LoginResponseDto login(@RequestBody @Valid AuthenticationDTO authenticationDTO) {

        authenticationManager = applicationContext.getBean(AuthenticationManager.class);

        var userAuth = new UsernamePasswordAuthenticationToken(authenticationDTO.username(), authenticationDTO.password());
        var auth = authenticationManager.authenticate(userAuth);
        String token = tokenService.generateToken((User) auth.getPrincipal());

        return new LoginResponseDto(token);
    }

    public User register(@RequestBody @Valid RegisterDto registerDto) {
        if (userRepository.findByUsername(registerDto.username()) != null) {
            return null;
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDto.password());
        User newUser = new User(registerDto.username(), encryptedPassword, registerDto.role() != null ? registerDto.role() : Role.valueOf("USER"));
        userRepository.save(newUser);
        return newUser;
    }

    public User getUser(String token) {
        if (token == null) {throw new IllegalArgumentException("Token must not be null");}
        try {
            token = token.replace("Bearer ", "");
            System.out.println("TOKEN AFTER REPLACE:" + token);
            String username = tokenService.validateToken(token);
            Optional<User> user = userRepository.findUserByUsername(username);
            return user.orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("An error occured when trying to access the user token", e);
        }
    }
}
