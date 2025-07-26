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
    private final UsuarioService service;

    public Produto salvar(Produto produto, Integer idUsuario) {
        this.definirFk(produto,idUsuario);
        return repository.save(produto);
    }

    public void definirFk(Produto produto, Integer idUsuario) {
        produto.setUsuario(service.buscarPorId(idUsuario));
    }

    public void deletar (Integer id) {
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

        return repository.save(produtoExistente);
    }

}
