package com.monitora.preco.repository;

import com.monitora.preco.entity.HistoricoPreco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoricoPrecoRepository extends JpaRepository<HistoricoPreco, Integer> {
}
