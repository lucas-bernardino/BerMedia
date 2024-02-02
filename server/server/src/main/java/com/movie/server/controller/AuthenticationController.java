package com.movie.server.controller;

import com.movie.server.model.dto.AuthenticationDTO;
import com.movie.server.model.dto.RegisterDto;
import com.movie.server.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/register")
    public ResponseEntity<Object> register (@RequestBody @Valid RegisterDto registerDto) {
        return authenticationService.register(registerDto);
    }
}
