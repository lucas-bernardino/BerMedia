package com.movie.server.controller;

import com.movie.server.model.User;
import com.movie.server.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("")
    public ResponseEntity<Void> createUser(@RequestBody @Valid User user) {
        User newUser = new User(user.getUsername(), user.getPassword());
        userService.createUser(newUser);
        return ResponseEntity.ok().build();
    }

    @GetMapping("")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok().body(users);
    }

    @GetMapping("/test")
    public String test() {
        return "Testing connection";
    }

}
