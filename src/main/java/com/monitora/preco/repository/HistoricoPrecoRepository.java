package com.monitora.preco.repository;

import com.monitora.preco.entity.HistoricoPreco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface HistoricoPrecoRepository extends JpaRepository<HistoricoPreco, Integer> {
    List<HistoricoPreco> findByProdutoIdAndProdutoUsuarioId(Integer idProduto, Integer idUsuario);

    @Modifying
    @Transactional
    @Query("DELETE FROM HistoricoPreco h WHERE h.produto.id = :produtoId")
    void deleteHistoricoPrecoWhereProdutoId(@Param("produtoId") Integer produtoId);

    @Query("SELECT COUNT(DISTINCT h.produto.id) FROM HistoricoPreco h WHERE h.produto.usuario.id = :idUsuario")
    int countProdutosMonitoradosPorUsuario(@Param("idUsuario") Integer idUsuario);

    boolean existsByProdutoIdAndPrecoColetadoLessThanEqual(Integer idProduto, BigDecimal limite);

    Optional<HistoricoPreco> findTopByProdutoIdOrderByDataColetaDesc(Integer produtoId);

    Optional<HistoricoPreco> findTopByProdutoIdAndProdutoUsuarioIdOrderByDataColetaDesc(Integer produtoId, Integer usuarioId);

    List<HistoricoPreco> findByProdutoIdAndProdutoUsuarioIdOrderByDataColetaAsc(Integer produtoId, Integer usuarioId);
}
