package com.movie.server.controller;


import com.movie.server.model.Media;
import com.movie.server.service.MediaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/media")
@Validated
public class MediaController {

    private final MediaService mediaService;

    public MediaController(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    @PostMapping("")
    public ResponseEntity<Void> createMedia(@RequestBody @Valid Media media) {
        mediaService.createMedia(media);
        return ResponseEntity.ok().build();
    }

    @GetMapping("")
    public ResponseEntity<List<Media>> getAllMediasByUsername(@RequestBody @Valid String username) {
        List<Media> medias = mediaService.getAllMediasByUsername(username);
        return ResponseEntity.ok().body(medias);
    }

}
