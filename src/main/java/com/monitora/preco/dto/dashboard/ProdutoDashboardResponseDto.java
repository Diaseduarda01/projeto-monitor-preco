package com.monitora.preco.dto.dashboard;

import java.math.BigDecimal;

public record ProdutoDashboardResponseDto(
        Integer id,
        String nome,
        BigDecimal precoDesejado,
        BigDecimal ultimoPreco,
        String status
) {}

