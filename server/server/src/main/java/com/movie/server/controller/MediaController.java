package com.movie.server.controller;


import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.movie.server.model.Media;
import com.movie.server.model.View;
import com.movie.server.service.MediaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @GetMapping("") // /media?username=...
    @JsonView(View.Default.class)
    public ResponseEntity<List<Media>> getAllMediasByUsername(@RequestParam String username) throws JsonProcessingException {
        List<Media> medias = mediaService.getAllMediasByUsername(username);
        return ResponseEntity.ok().body(medias);
    }

    @GetMapping("/all")
    @JsonView(View.Test.class)
    public ResponseEntity<List<Media>> getAllMedias() {
        List<Media> medias = mediaService.getAllMedias();
        return ResponseEntity.ok().body(medias);
    }


}
