package com.monitora.preco.dto.produto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProdutoRequestDto (

        @NotBlank
        String nome,

        @NotBlank
         String url,

        @NotNull
         Double precoDesejado,

        @NotBlank
         Boolean ativo
){
}
