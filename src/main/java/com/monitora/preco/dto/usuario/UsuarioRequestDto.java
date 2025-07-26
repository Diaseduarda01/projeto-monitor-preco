package com.monitora.preco.dto.usuario;

import jakarta.validation.constraints.NotNull;
import lombok.*;

public record UsuarioRequestDto (
        @NotNull
         String nome,

         @NotNull
          String email,

         @NotNull
          String senha
) {
}