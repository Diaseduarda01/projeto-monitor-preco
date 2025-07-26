package com.monitora.preco.dto.produto;

import java.math.BigDecimal;
import java.math.BigInteger;

public record ProdutoResponseDto (
        Integer id,
        String nome,
        String url,
        String classe,
        BigDecimal precoDesejado,
        Boolean ativo,
        UsuarioResponse usuario
){
    public record UsuarioResponse (
             Integer id,
             String nome,
             String email,
             String senha
    ){
    }
}
