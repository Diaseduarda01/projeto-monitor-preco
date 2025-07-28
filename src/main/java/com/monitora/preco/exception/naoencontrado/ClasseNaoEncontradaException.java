package com.monitora.preco.exception.naoencontrado;

import com.monitora.preco.exception.naoencontrado.base.NaoEncontradoException;

public class ClasseNaoEncontradaException extends NaoEncontradoException {

    public static final String MESSAGE = "Classe não encontrada";

    public ClasseNaoEncontradaException() {
        super(MESSAGE);
    }

    public ClasseNaoEncontradaException(String message, Throwable cause) {
        super(message, cause);
    }
}
