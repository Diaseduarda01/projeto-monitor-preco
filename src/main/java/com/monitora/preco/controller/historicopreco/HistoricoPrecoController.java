package com.monitora.preco.controller;

import com.monitora.preco.dto.dashboard.ProdutoDashboardResponseDto;
import com.monitora.preco.dto.historicopreco.HistoricoPrecoResponseDto;
import com.monitora.preco.entity.HistoricoPreco;
import com.monitora.preco.service.HistoricoPrecoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/historico")
@RequiredArgsConstructor
public class HistoricoPrecoController {

    private final HistoricoPrecoService service;

    @GetMapping("/{produtoId}/{usuarioId}")
    public ResponseEntity<List<HistoricoPrecoResponseDto>> listarHistoricoPorIdProduto(
            @PathVariable Integer produtoId,
            @PathVariable Integer usuarioId) {

        List<HistoricoPreco> historico = service.listarHistoricoPorProdutoEUsuario(produtoId, usuarioId);

        List<HistoricoPrecoResponseDto> resposta = historico.stream()
                .map(h -> new HistoricoPrecoResponseDto(h.getPrecoColetado(), h.getDataColeta()))
                .toList();

        return ResponseEntity.ok(resposta);
    }

    @GetMapping("/monitorados/{usuarioId}")
    public ResponseEntity<Integer> totalMonitorados(@PathVariable Integer usuarioId) {
        return ResponseEntity.ok(service.qtdProdutosMonitoradosPorusuario(usuarioId));
    }

    @GetMapping("/proximos-preco-desejado/{usuarioId}")
    public ResponseEntity<Integer> produtosProximosPrecoDesejado(@PathVariable Integer usuarioId) {
        return ResponseEntity.ok(service.qtdProdutoPertoDoPrecoDesejado(usuarioId));
    }

    @GetMapping("/no-preco-desejado/{usuarioId}")
    public ResponseEntity<Integer> produtosNoOuAbaixoPrecoDesejado(@PathVariable Integer usuarioId) {
        return ResponseEntity.ok(service.qtdProdutosNoPrecoDesejado(usuarioId));
    }

    @GetMapping("/lista-produtos/{usuarioId}")
    public ResponseEntity<List<ProdutoDashboardResponseDto>> listarProdutosParaDashboard(@PathVariable Integer usuarioId) {
        List<ProdutoDashboardResponseDto> produtos = service.listarProdutosParaDashboard(usuarioId);
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/preco-desejado/{produtoId}/{usuarioId}")
    public ResponseEntity<BigDecimal> getPrecoDesejado(@PathVariable Integer produtoId, @PathVariable Integer usuarioId) {
        return ResponseEntity.ok(service.getPrecoDesejado(produtoId, usuarioId));
    }

    @GetMapping("/preco-atual/{produtoId}/{usuarioId}")
    public ResponseEntity<BigDecimal> getUltimoPreco(@PathVariable Integer produtoId, @PathVariable Integer usuarioId) {
        return ResponseEntity.ok(service.getUltimoPreco(produtoId, usuarioId));
    }

    @GetMapping("/variacao-porcentual/{produtoId}/{usuarioId}")
    public ResponseEntity<BigDecimal> getVariacao(@PathVariable Integer produtoId, @PathVariable Integer usuarioId) {
        return ResponseEntity.ok(service.getVariacaoPercentual(produtoId, usuarioId));
    }

    @GetMapping("/qtd-dias-monitoramento/{produtoId}/{usuarioId}")
    public ResponseEntity<Long> getTempoMonitorado(@PathVariable Integer produtoId, @PathVariable Integer usuarioId) {
        return ResponseEntity.ok(service.getDiasDeMonitoramento(produtoId, usuarioId));
    }
}
