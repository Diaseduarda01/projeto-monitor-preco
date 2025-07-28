package com.monitora.preco.dto.auth;

public record AuthResponseDto(
        String token,
        String nome,
        String role
) {}

