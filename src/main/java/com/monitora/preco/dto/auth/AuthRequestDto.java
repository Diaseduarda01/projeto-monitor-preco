package com.monitora.preco.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record AuthRequestDto(
        @NotBlank String email,
        @NotBlank String senha
) {
}
