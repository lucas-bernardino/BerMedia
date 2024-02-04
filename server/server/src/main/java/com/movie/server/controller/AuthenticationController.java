package com.movie.server.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.movie.server.model.User;
import com.movie.server.model.View;
import com.movie.server.model.dto.AuthenticationDTO;
import com.movie.server.model.dto.LoginResponseDto;
import com.movie.server.model.dto.RegisterDto;
import com.movie.server.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login (@RequestBody @Valid AuthenticationDTO authenticationDTO) {
        LoginResponseDto loginResponseDto = authenticationService.login(authenticationDTO);
        return ResponseEntity.ok().body(loginResponseDto);
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> register (@RequestBody @Valid RegisterDto registerDto) {
        User user = authenticationService.register(registerDto);
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    @JsonView(View.Default.class)
    public ResponseEntity<User> getUser (@RequestHeader("Authorization") String token) {
        User user = authenticationService.getUser(token);
        if (user == null ) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(user);
    }
}
