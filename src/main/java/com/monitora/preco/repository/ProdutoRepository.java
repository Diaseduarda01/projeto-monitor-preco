package com.monitora.preco.repository;

import com.monitora.preco.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, BigInteger> {
}
