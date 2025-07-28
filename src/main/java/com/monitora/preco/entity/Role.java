package com.monitora.preco.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    ADMIN(1),
    COMUM(2);

    private final Integer codigo;
}