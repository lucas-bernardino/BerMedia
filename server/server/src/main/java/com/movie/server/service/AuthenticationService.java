package com.movie.server.service;

import com.movie.server.auth.TokenService;
import com.movie.server.model.User;
import com.movie.server.model.dto.AuthenticationDTO;
import com.movie.server.model.dto.LoginResponseDto;
import com.movie.server.model.dto.RegisterDto;
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

    public ResponseEntity<Object> login(@RequestBody @Valid AuthenticationDTO authenticationDTO) {
        authenticationManager = applicationContext.getBean(AuthenticationManager.class);

        var userAuth = new UsernamePasswordAuthenticationToken(authenticationDTO.username(), authenticationDTO.password());
        var auth = authenticationManager.authenticate(userAuth);
        String token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDto(token));
    }

    public ResponseEntity<Object> register(@RequestBody @Valid RegisterDto registerDto) {
        if (userRepository.findByUsername(registerDto.username()) != null) {
            return ResponseEntity.badRequest().build();
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDto.password());
        User newUser = new User(registerDto.username(), encryptedPassword, registerDto.role());
        userRepository.save(newUser);
        return ResponseEntity.ok().build();
    }
}
