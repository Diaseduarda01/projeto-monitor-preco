package com.monitora.preco.dto.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

public record UsuarioRequestDto (
        @NotNull
         String nome,

         @NotNull
          String email,

         @NotNull
          String senha,

        @NotBlank
        RoleRequestDto role

) {
    public record RoleRequestDto(

            @NotBlank
            String nome
    ) {}
}