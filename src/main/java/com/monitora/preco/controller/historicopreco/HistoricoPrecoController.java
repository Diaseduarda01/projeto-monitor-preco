package com.monitora.preco.controller.historicopreco;

import com.monitora.preco.dto.dashboard.ProdutoDashboardResponseDto;
import com.monitora.preco.dto.historicopreco.HistoricoPrecoResponseDto;
import com.monitora.preco.entity.HistoricoPreco;
import com.monitora.preco.service.HistoricoPrecoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/historico")
@RequiredArgsConstructor
public class HistoricoPrecoController implements HistoricoPrecoControllerDoc {

    @Autowired
    private final HistoricoPrecoService service;


    @Override
    public ResponseEntity<List<HistoricoPrecoResponseDto>> listarHistoricoPorIdProduto(Integer produtoId, Integer usuarioId) {
        List<HistoricoPreco> historico = service.listarHistoricoPorProdutoEUsuario(produtoId, usuarioId);

        List<HistoricoPrecoResponseDto> resposta = historico.stream()
                .map(h -> new HistoricoPrecoResponseDto(h.getPrecoColetado(), h.getDataColeta()))
                .toList();

        return ResponseEntity.ok(resposta);
    }

    @Override
    public ResponseEntity<List<ProdutoDashboardResponseDto>> listarProdutosParaDashboard(Integer usuarioId) {
        List<ProdutoDashboardResponseDto> produtos = service.listarProdutosParaDashboard(usuarioId);
        return ResponseEntity.ok(produtos);
    }

    @Override
    public ResponseEntity<Integer> totalMonitorados(Integer usuarioId) {
        return ResponseEntity.ok(service.qtdProdutosMonitoradosPorusuario(usuarioId));
    }

    @Override
    public ResponseEntity<Integer> produtosProximosPrecoDesejado(Integer usuarioId) {
        return ResponseEntity.ok(service.qtdProdutoPertoDoPrecoDesejado(usuarioId));
    }

    @Override
    public ResponseEntity<Integer> produtosNoOuAbaixoPrecoDesejado(Integer usuarioId) {
        return ResponseEntity.ok(service.qtdProdutosNoPrecoDesejado(usuarioId));
    }

    @Override
    public ResponseEntity<Long> getTempoMonitorado(Integer produtoId, Integer usuarioId) {
        return ResponseEntity.ok(service.getDiasDeMonitoramento(produtoId, usuarioId));
    }

    @Override
    public ResponseEntity<BigDecimal> getPrecoDesejado(Integer produtoId, Integer usuarioId) {
        return ResponseEntity.ok(service.getPrecoDesejado(produtoId, usuarioId));
    }

    @Override
        public ResponseEntity<BigDecimal> getUltimoPreco(Integer produtoId, Integer usuarioId) {
            return ResponseEntity.ok(service.getUltimoPreco(produtoId, usuarioId));
    }

    @Override
    public ResponseEntity<BigDecimal> getVariacao(Integer produtoId, Integer usuarioId) {
        return ResponseEntity.ok(service.getVariacaoPercentual(produtoId, usuarioId));
    }
}
