package com.monitora.preco.dto.usuario;

import com.monitora.preco.entity.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UsuarioMapper {

    @Mapping(target = "role", ignore = true)
    Usuario toEntity(UsuarioRequestDto dto);

    UsuarioResponseDto toResponseDto(Usuario usuario);
}