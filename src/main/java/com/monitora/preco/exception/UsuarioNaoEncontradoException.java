package com.monitora.preco.exception;

import com.monitora.preco.exception.base.NaoEncontradoException;

public class UsuarioNaoEncontradoException extends NaoEncontradoException {
    public static final String MESSAGE = "Usuário não encontrado";

    public UsuarioNaoEncontradoException() {
        super(MESSAGE);
    }

    public UsuarioNaoEncontradoException(String message, Throwable cause) {
        super(message, cause);
    }
}
