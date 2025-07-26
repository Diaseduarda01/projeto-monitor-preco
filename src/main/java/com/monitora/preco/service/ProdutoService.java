package com.monitora.preco.service;

import com.monitora.preco.entity.Produto;
import com.monitora.preco.exception.ProdutoNaoEncontrado;
import com.monitora.preco.repository.ProdutoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
@AllArgsConstructor
public class ProdutoService {

    private final ProdutoRepository repository;

    public Produto salvar(Produto produto) {
        return repository.save(produto);
    }

    public void deletar (Integer id) {
        Produto produto = buscarPorId(id);
        repository.deleteById(id);
    }

    public Produto buscarPorId(Integer id) {
        return repository.findById(id).orElseThrow(ProdutoNaoEncontrado::new);
    }

    public List<Produto> buscarTodos() {
        return repository.findAll();
    }

    public Produto atualizar(Integer id, Produto produto) {
        Produto produtoExistente = buscarPorId(id);

        produtoExistente.setNome(produto.getNome());
        produtoExistente.setUrl(produto.getUrl());
        produtoExistente.setAtivo(produto.getAtivo());
        produtoExistente.setPrecoDesejado(produto.getPrecoDesejado());

        return salvar(produtoExistente);
    }

}
