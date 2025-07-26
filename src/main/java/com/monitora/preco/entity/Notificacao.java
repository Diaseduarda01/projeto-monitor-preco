package com.monitora.preco.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Notificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private BigDecimal precoAlvo;
    private BigDecimal precoAtingido;
    private Boolean enviado;

    @ManyToOne
    private Usuario usuario;

    @ManyToOne
    private Produto produto;
}
