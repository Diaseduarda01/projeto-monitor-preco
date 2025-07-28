package com.monitora.preco.exception.naoencontrado;

import com.monitora.preco.exception.naoencontrado.base.NaoEncontradoException;

public class UsuarioNaoEncontradoException extends NaoEncontradoException {
    public static final String MESSAGE = "Usuário não encontrado";

    public UsuarioNaoEncontradoException() {
        super(MESSAGE);
    }

    public UsuarioNaoEncontradoException(String message, Throwable cause) {
        super(message, cause);
    }
}
