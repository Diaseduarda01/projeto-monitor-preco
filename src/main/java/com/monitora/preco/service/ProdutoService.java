package com.monitora.preco.service;

import com.monitora.preco.entity.Produto;
import com.monitora.preco.exception.naoencontrado.ProdutoNaoEncontradoException;
import com.monitora.preco.repository.HistoricoPrecoRepository;
import com.monitora.preco.repository.ProdutoRepository;
import com.monitora.preco.utils.LoggerUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class ProdutoService {

    private final ProdutoRepository repository;
    private final UsuarioService service;
    private final HistoricoPrecoRepository historicoPrecoRepository;

    public Produto salvar(Produto produto, Integer idUsuario) {
        this.definirFk(produto, idUsuario);
        Produto salvo = repository.save(produto);
        LoggerUtils.logProduto("SALVAR", salvo.getNome(), "Produto salvo com ID: " + salvo.getId());
        return salvo;
    }

    public void definirFk(Produto produto, Integer idUsuario) {
        produto.setUsuario(service.buscarPorId(idUsuario));
    }

    @Transactional
    public void deletar(Integer id) {
        historicoPrecoRepository.deleteHistoricoPrecoWhereProdutoId(id);
        repository.deleteById(id);
        LoggerUtils.logProduto("DELETAR", "ID " + id, "Produto e histórico removidos");
    }

    public Produto buscarPorId(Integer id) {
        return repository.findById(id).orElseThrow(() -> {
            LoggerUtils.error("Produto com ID " + id + " não encontrado");
            return new ProdutoNaoEncontradoException();
        });
    }

    public List<Produto> buscarTodos() {
        List<Produto> lista = repository.findAll();
        LoggerUtils.info("Total de produtos encontrados: " + lista.size());
        return lista;
    }

    public Produto atualizar(Integer id, Produto produto) {
        Produto produtoExistente = buscarPorId(id);

        produtoExistente.setNome(produto.getNome());
        produtoExistente.setUrl(produto.getUrl());
        produtoExistente.setClasse(produto.getClasse());
        produtoExistente.setAtivo(produto.getAtivo());
        produtoExistente.setPrecoDesejado(produto.getPrecoDesejado());

        Produto atualizado = repository.save(produtoExistente);
        LoggerUtils.logProduto("ATUALIZAR", atualizado.getNome(), "Produto atualizado com sucesso");
        return atualizado;
    }

    public List<Produto> buscarProdutosAtivos() {
        List<Produto> ativos = repository.findByAtivoTrue();
        LoggerUtils.info("Total de produtos ativos: " + ativos.size());
        return ativos;
    }
}