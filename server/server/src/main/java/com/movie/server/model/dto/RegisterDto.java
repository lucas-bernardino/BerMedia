package com.movie.server.model.dto;

import com.movie.server.model.enums.Role;
import jakarta.validation.constraints.NotNull;

public record RegisterDto(
        @NotNull String username,
        @NotNull String password,
        Role role
) {
}
