package com.monitora.preco.utils;

import com.monitora.preco.entity.Role;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RoleConverter implements AttributeConverter<Role, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Role role) {
        return role != null ? role.getCodigo() : null;
    }

    @Override
    public Role convertToEntityAttribute(Integer codigo) {
        if (codigo == null) return null;
        for (Role r : Role.values()) {
            if (r.getCodigo().equals(codigo)) return r;
        }
        throw new IllegalArgumentException("Código inválido para Role: " + codigo);
    }
}
