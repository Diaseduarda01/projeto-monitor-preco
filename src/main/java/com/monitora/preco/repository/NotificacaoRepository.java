package com.monitora.preco.repository;

import com.monitora.preco.entity.Notificacao;
import com.monitora.preco.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface NotificacaoRepository extends JpaRepository<Notificacao, Integer> {
    Boolean existsByProdutoAndPrecoAtingido(Produto produto, BigDecimal precoAtual);
}
