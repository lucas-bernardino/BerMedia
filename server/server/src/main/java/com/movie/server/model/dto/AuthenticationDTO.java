package com.movie.server.model.dto;

import jakarta.validation.constraints.NotNull;

public record AuthenticationDTO(
        @NotNull String username,
        @NotNull String password
) {
}
