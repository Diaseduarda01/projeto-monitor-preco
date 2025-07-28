package com.monitora.preco.repository;

import com.monitora.preco.entity.Relatorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelatorioRepository extends JpaRepository<Relatorio, Integer> {
    List<Relatorio> findByUsuarioId(Integer usuarioId);
}
