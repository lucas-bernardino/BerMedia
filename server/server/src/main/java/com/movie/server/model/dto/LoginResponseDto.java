package com.movie.server.model.dto;

import jakarta.validation.constraints.NotNull;

public record LoginResponseDto(@NotNull String token) {
}
