package com.monitora.preco.dto.produto;

import com.monitora.preco.entity.Produto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProdutoMapper {

    Produto toEntity(ProdutoRequestDto dto);

    @Mapping(target = "usuario", expression = "java(toUsuarioResponse(produto))")
    ProdutoResponseDto toResponse(Produto produto);

    default ProdutoResponseDto.UsuarioResponse toUsuarioResponse(Produto produto) {
        if (produto == null || produto.getUsuario() == null) {
            return null;
        }

        var usuario = produto.getUsuario();

        return new ProdutoResponseDto.UsuarioResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getSenha()
        );
    }
}
