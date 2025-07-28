package com.monitora.preco.service;

import com.monitora.preco.entity.HistoricoPreco;
import com.monitora.preco.repository.HistoricoPrecoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoricoPrecoService {

    private final HistoricoPrecoRepository repository;
    private final ProdutoService produtoService;

    public HistoricoPreco salvar(HistoricoPreco historicoPreco, Integer idProduto) {
        this.definirFk(historicoPreco, idProduto);
        return repository.save(historicoPreco);
    }

    public void definirFk(HistoricoPreco historicoPreco, Integer idProduto) {
        historicoPreco.setProduto(produtoService.buscarPorId(idProduto));
    }

    public List<HistoricoPreco> listarHistoricoPorIdProduto(Integer idProduto) {
        return repository.findByProdutoId(idProduto);
    }
}
