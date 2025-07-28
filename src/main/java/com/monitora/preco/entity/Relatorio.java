package com.monitora.preco.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Relatorio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String tipo;
    private String caminhoArquivo;

    @Column(name = "data_geracao", updatable = false, insertable = false)
    private LocalDateTime dataGeracao;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}
