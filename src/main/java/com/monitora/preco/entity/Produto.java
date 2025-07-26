package com.monitora.preco.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;

    @Column(length = 1_000)
    private String url;
    private String classe;
    private BigDecimal precoDesejado;
    private Boolean ativo;

    @ManyToOne
    private Usuario usuario;

    @OneToMany(mappedBy = "produto")
    private List<HistoricoPreco> historicoPrecos;
}
