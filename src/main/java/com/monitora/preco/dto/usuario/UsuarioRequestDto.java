package com.monitora.preco.dto.usuario;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioRequestDto {

    @NotNull
    private String nome;

    @NotNull
    private String email;

    @NotNull
    private String senha;
}