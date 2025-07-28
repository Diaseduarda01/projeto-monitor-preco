package com.monitora.preco.exception.naoencontrado;

import com.monitora.preco.exception.naoencontrado.base.NaoEncontradoException;

public class PrecoNaoEncontradoException extends NaoEncontradoException {
    public static final String MESSAGE = "Preço não encontrado";

    public PrecoNaoEncontradoException() {
        super(MESSAGE);
    }
}
