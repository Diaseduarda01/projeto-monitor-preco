package com.monitora.preco.dto.role;

import jakarta.validation.constraints.NotBlank;

public record RoleRequestDto(

        @NotBlank
        String nome
) {
}
