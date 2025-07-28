package com.monitora.preco.dto.historicopreco;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record HistoricoPrecoResponseDto(
  BigDecimal precoColetado,
  LocalDateTime dataColetada
){}
