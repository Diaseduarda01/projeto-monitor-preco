package com.monitora.preco.dto.relatorio;


import java.time.LocalDateTime;

public record RelatorioResponseDto(
        Integer id,
        String tipo,
        String urlDownload,
        LocalDateTime dataGeracao
) {}