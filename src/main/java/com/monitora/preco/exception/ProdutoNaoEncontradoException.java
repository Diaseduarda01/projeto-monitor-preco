package com.monitora.preco.exception;

import com.monitora.preco.exception.base.NaoEncontradoException;

public class ProdutoNaoEncontrado extends NaoEncontradoException {
  public static final String MESSAGE = "Produto não encontrado";

  public ProdutoNaoEncontrado() {
    super(MESSAGE);
  }

  public ProdutoNaoEncontrado(String message, Throwable cause) {
    super(message, cause);
  }
}
