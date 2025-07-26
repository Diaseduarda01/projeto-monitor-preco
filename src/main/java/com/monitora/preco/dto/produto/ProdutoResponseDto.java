package com.monitora.preco.dto.produto;

import java.math.BigInteger;

public record ProdutoResponseDto (
        BigInteger id,
        String nome,
        String url,
        Double precoDesejado,
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
