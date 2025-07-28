package com.monitora.preco.service;

import com.monitora.preco.dto.dashboard.ProdutoDashboardResponseDto;
import com.monitora.preco.entity.HistoricoPreco;
import com.monitora.preco.entity.Produto;
import com.monitora.preco.exception.naoencontrado.ProdutoNaoEncontradoException;
import com.monitora.preco.repository.HistoricoPrecoRepository;
import com.monitora.preco.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoricoPrecoService {

    private final HistoricoPrecoRepository repository;
    private final ProdutoRepository produtoRepository;
    private final ProdutoService produtoService;

    public HistoricoPreco salvar(HistoricoPreco historicoPreco, Integer idProduto) {
        this.definirFk(historicoPreco, idProduto);
        return repository.save(historicoPreco);
    }

    public void definirFk(HistoricoPreco historicoPreco, Integer idProduto) {
        historicoPreco.setProduto(produtoService.buscarPorId(idProduto));
    }

    public List<HistoricoPreco> listarHistoricoPorProdutoEUsuario(Integer idProduto, Integer idUsuario) {
        return repository.findByProdutoIdAndProdutoUsuarioId(idProduto, idUsuario);
    }

    public Integer qtdProdutosMonitoradosPorusuario(Integer idusuario) {
        return repository.countProdutosMonitoradosPorUsuario(idusuario);
    }

    public Integer qtdProdutoPertoDoPrecoDesejado(Integer idUsuario) {
        BigDecimal margem = new BigDecimal("1.05");
        List<Produto> produtos = produtoRepository.findByUsuarioId(idUsuario);

        int contador = 0;
        for (Produto produto : produtos) {
            if (produto.getPrecoDesejado() == null) continue;

            BigDecimal limite = produto.getPrecoDesejado().multiply(margem);
            boolean temPrecoPerto = repository
                    .existsByProdutoIdAndPrecoColetadoLessThanEqual(produto.getId(), limite);

            if (temPrecoPerto) {
                contador++;
            }
        }
        return contador;
    }

    public Integer qtdProdutosNoPrecoDesejado(Integer idUsuario) {
        List<Produto> produtos = produtoRepository.findByUsuarioId(idUsuario);
        int contador = 0;

        for (Produto produto : produtos) {
            if (produto.getPrecoDesejado() == null) continue;

            boolean noPrecoOuMenor = repository
                    .existsByProdutoIdAndPrecoColetadoLessThanEqual(produto.getId(), produto.getPrecoDesejado());

            if (noPrecoOuMenor) {
                contador++;
            }
        }

        return contador;
    }

    public List<ProdutoDashboardResponseDto> listarProdutosParaDashboard(Integer usuarioId) {
        List<Produto> produtos = produtoRepository.findByUsuarioId(usuarioId);
        List<ProdutoDashboardResponseDto> resposta = new ArrayList<>();

        for (Produto produto : produtos) {
            BigDecimal precoDesejado = produto.getPrecoDesejado();
            BigDecimal ultimoPreco = repository.findTopByProdutoIdOrderByDataColetaDesc(produto.getId())
                    .map(HistoricoPreco::getPrecoColetado)
                    .orElse(null);

            String status;

            if (precoDesejado == null || ultimoPreco == null) {
                status = "SEM DADOS";
            } else if (ultimoPreco.compareTo(precoDesejado) <= 0) {
                status = "ATINGIU";
            } else {
                BigDecimal margem = precoDesejado.multiply(new BigDecimal("1.05"));
                status = ultimoPreco.compareTo(margem) <= 0 ? "PERTO" : "LONGE";
            }

            resposta.add(new ProdutoDashboardResponseDto(
                    produto.getId(),
                    produto.getNome(),
                    precoDesejado,
                    ultimoPreco,
                    status
            ));
        }

        return resposta;
    }

    public BigDecimal getPrecoDesejado(Integer produtoId, Integer usuarioId) {
        Produto produto = produtoRepository.findByIdAndUsuarioId(produtoId, usuarioId)
                .orElseThrow(ProdutoNaoEncontradoException::new);
        return produto.getPrecoDesejado();
    }

    public BigDecimal getUltimoPreco(Integer produtoId, Integer usuarioId) {
        return repository
                .findTopByProdutoIdAndProdutoUsuarioIdOrderByDataColetaDesc(produtoId, usuarioId)
                .map(HistoricoPreco::getPrecoColetado)
                .orElse(null);
    }

    public BigDecimal getVariacaoPercentual(Integer produtoId, Integer usuarioId) {
        List<HistoricoPreco> historicos = repository.findByProdutoIdAndProdutoUsuarioIdOrderByDataColetaAsc(produtoId, usuarioId);

        if (historicos.size() < 2) return BigDecimal.ZERO;

        BigDecimal primeiro = historicos.get(0).getPrecoColetado();
        BigDecimal ultimo = historicos.get(historicos.size() - 1).getPrecoColetado();

        return ultimo.subtract(primeiro)
                .divide(primeiro, 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
    }

    public Long getDiasDeMonitoramento(Integer produtoId, Integer usuarioId) {
        List<HistoricoPreco> historicos = repository.findByProdutoIdAndProdutoUsuarioIdOrderByDataColetaAsc(produtoId, usuarioId);

        if (historicos.size() < 2) return 0L;

        LocalDate inicio = historicos.get(0).getDataColeta().toLocalDate();
        LocalDate fim = historicos.get(historicos.size() - 1).getDataColeta().toLocalDate();

        return ChronoUnit.DAYS.between(inicio, fim);
    }
}
