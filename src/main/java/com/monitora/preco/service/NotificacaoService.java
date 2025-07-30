package com.monitora.preco.service;

import com.monitora.preco.entity.Notificacao;
import com.monitora.preco.entity.Produto;
import com.monitora.preco.repository.NotificacaoRepository;
import com.monitora.preco.utils.LoggerUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class NotificacaoService {

    private final NotificacaoRepository repository;
    private final ProdutoService produtoService;
    private final UsuarioService usuarioService;

    public Boolean jaNotificou(Produto produto, BigDecimal precoAtual) {
        boolean existe = repository.existsByProdutoAndPrecoAtingido(produto, precoAtual);
        LoggerUtils.info("Resultado da verificação: " + (existe ? "já notificou" : "ainda não notificou"));
        return existe;
    }

    public Notificacao salvar(Notificacao notificacao, Integer idUsuario, Integer idProduto) {
        this.definirFk(notificacao, idUsuario, idProduto);
        Notificacao salva = repository.save(notificacao);
        return salva;
    }

    public void definirFk(Notificacao notificacao, Integer idUsuario, Integer idProduto) {
        notificacao.setProduto(produtoService.buscarPorId(idProduto));
        notificacao.setUsuario(usuarioService.buscarPorId(idUsuario));
        LoggerUtils.info("Relacionamentos definidos com sucesso: Produto=" +
                notificacao.getProduto().getNome() + ", Usuário=" + notificacao.getUsuario().getNome());
    }
}