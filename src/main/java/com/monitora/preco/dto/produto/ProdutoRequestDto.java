package com.monitora.preco.dto.produto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProdutoRequestDto (

        @NotBlank
        String nome,

        @NotBlank
         String url,

        @NotBlank
        String classe,

        @NotNull
        BigDecimal precoDesejado,

        @NotBlank
         Boolean ativo,

        @NotNull
        @Positive
        Integer idUsuario
){
}
