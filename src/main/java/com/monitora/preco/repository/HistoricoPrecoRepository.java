package com.monitora.preco.repository;

import com.monitora.preco.entity.HistoricoPreco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoricoPrecoRepository extends JpaRepository<HistoricoPreco, Integer> {
    List<HistoricoPreco> findByProdutoIdAndProdutoUsuarioId(Integer idProduto, Integer idUsuario);
}
