package com.monitora.preco.repository;

import com.monitora.preco.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
    List<Produto> findByAtivoTrue();

    List<Produto> findByUsuarioId(Integer idUsuario);

    Optional<Produto> findByIdAndUsuarioId(Integer produtoId, Integer usuarioId);
}
