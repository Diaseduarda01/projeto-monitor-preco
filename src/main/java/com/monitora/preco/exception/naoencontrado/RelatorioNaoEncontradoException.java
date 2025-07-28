package com.monitora.preco.exception.naoencontrado;

import com.monitora.preco.exception.naoencontrado.base.NaoEncontradoException;

public class RelatorioNaoEncontradoException extends NaoEncontradoException {
  public static final String MESSAGE = "Relatório não encontrado";
    public RelatorioNaoEncontradoException() {
        super(MESSAGE);
    }
}
