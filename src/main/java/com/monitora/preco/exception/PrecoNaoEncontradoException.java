package com.monitora.preco.exception;

import com.monitora.preco.exception.base.NaoEncontradoException;

public class PrecoNaoEncontradoException extends NaoEncontradoException {
    public static final String MESSAGE = "Preço não encontrado";

    public PrecoNaoEncontradoException() {
        super(MESSAGE);
    }
}
