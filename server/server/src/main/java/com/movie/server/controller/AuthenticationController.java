package com.movie.server.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.movie.server.model.User;
import com.movie.server.model.View;
import com.movie.server.model.dto.AuthenticationDTO;
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
    public ResponseEntity<Object> login (@RequestBody @Valid AuthenticationDTO authenticationDTO) {
        return authenticationService.login(authenticationDTO);
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> register (@RequestBody @Valid RegisterDto registerDto) {
        System.out.println("REGISTER DTO: " + registerDto.username() + registerDto.password());
        return authenticationService.register(registerDto);
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
