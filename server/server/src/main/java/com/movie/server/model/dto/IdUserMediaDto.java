package com.movie.server.model.dto;

import jakarta.validation.constraints.NotNull;

public record IdUserMediaDto(
        @NotNull String imdbId,
        @NotNull Long userId
) {}
