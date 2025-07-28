package com.monitora.preco.dto.usuario;

public record UsuarioResponseDto (
        Integer id,
        String nome,
        String email,
        RoleResponseDto role
){
    public record RoleResponseDto(

            Integer id,
            String nome
    ) {}
}