package com.monitora.preco.exception;

import com.monitora.preco.exception.base.NaoEncontradoException;

public class ProdutoNaoEncontradoException extends NaoEncontradoException {
  public static final String MESSAGE = "Produto não encontrado";

  public ProdutoNaoEncontradoException() {
    super(MESSAGE);
  }

}
