package com.monitora.preco.controller;

import com.monitora.preco.dto.historicopreco.HistoricoPrecoResponseDto;
import com.monitora.preco.entity.HistoricoPreco;
import com.monitora.preco.service.HistoricoPrecoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/historico")
@RequiredArgsConstructor
public class HistoricoPrecoController {

    private final HistoricoPrecoService service;

    @GetMapping("/{idProduto}")
    public ResponseEntity<List<HistoricoPrecoResponseDto>> listarHistoricoPorIdProduto(@PathVariable Integer idProduto) {
        List<HistoricoPreco> historico = service.listarHistoricoPorIdProduto(idProduto);
        List<HistoricoPrecoResponseDto> resposta = historico.stream()
                .map(h -> new HistoricoPrecoResponseDto(h.getPrecoColetado(), h.getDataColeta()))
                .toList();
        return ResponseEntity.ok(resposta);
    }

}
