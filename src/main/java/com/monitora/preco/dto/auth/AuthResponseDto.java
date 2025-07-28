package com.monitora.preco.dto.auth;

public record AuthResponseDto(
        String token,
        String nome,
        RoleResponseDto roleResponseDto
) {
    public record RoleResponseDto(

            Integer id,
            String nome
    ) {}
}

