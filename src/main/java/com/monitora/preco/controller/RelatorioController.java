package com.monitora.preco.controller;

import com.monitora.preco.dto.relatorio.RelatorioResponseDto;
import com.monitora.preco.entity.Relatorio;
import com.monitora.preco.service.RelatorioService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("relatorios")
@RequiredArgsConstructor
public class RelatorioController {

    private final RelatorioService service;

    @PostMapping("/gerar-relatorio/{usuarioId}")
    public ResponseEntity<?> gerarRelatorio(
            @PathVariable Integer usuarioId,
            @RequestParam String tipo
    ) {
        if (!tipo.equalsIgnoreCase("semanal") && !tipo.equalsIgnoreCase("mensal")) {
            return ResponseEntity.badRequest().body("Tipo de relatório inválido. Use 'semanal' ou 'mensal'.");
        }

        Relatorio relatorio = service.gerarRelatorio(usuarioId, tipo);
        return ResponseEntity.status(HttpStatus.CREATED).body("Relatório gerado com sucesso. ID: " + relatorio.getId());
    }

    @GetMapping("/listar-relatorios/{usuarioId}")
    public ResponseEntity<List<RelatorioResponseDto>> listarRelatorios(@PathVariable Integer usuarioId) {
        List<RelatorioResponseDto> relatorios = service.listarRelatorios(usuarioId);
        return ResponseEntity.ok(relatorios);
    }

    @GetMapping("/download/{relatorioId}")
    public ResponseEntity<FileSystemResource> downloadRelatorio(@PathVariable Integer relatorioId) {
        FileSystemResource arquivo = service.downloadRelatorio(relatorioId);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + arquivo.getFilename() + "\"")
                .body(arquivo);
    }
}
