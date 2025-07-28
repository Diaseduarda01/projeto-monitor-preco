package com.monitora.preco.exception.naoencontrado;

import com.monitora.preco.exception.naoencontrado.base.NaoEncontradoException;

public class RoleNaoEncontradoException extends NaoEncontradoException {
    public static final String MESSAGE = "Role não encontrado";
    public RoleNaoEncontradoException() {
        super(MESSAGE);
    }
}
