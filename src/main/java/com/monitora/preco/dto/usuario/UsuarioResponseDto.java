package com.monitora.preco.dto.usuario;

public record UsuarioResponseDto (
        Integer id,
        String nome,
        String email,
        String senha
){
}