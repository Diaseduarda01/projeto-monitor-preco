package com.monitora.preco.exception.naoencontrado;

import com.monitora.preco.exception.naoencontrado.base.NaoEncontradoException;

public class ProdutoNaoEncontradoException extends NaoEncontradoException {
  public static final String MESSAGE = "Produto não encontrado";

  public ProdutoNaoEncontradoException() {
    super(MESSAGE);
  }

}
