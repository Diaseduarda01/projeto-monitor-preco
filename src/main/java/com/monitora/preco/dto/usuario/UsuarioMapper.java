package com.monitora.preco.dto.usuario;

import com.monitora.preco.entity.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UsuarioMapper {

    Usuario toEntity(UsuarioRequestDto dto);

    UsuarioResponseDto toResponseDto(Usuario usuario);
}