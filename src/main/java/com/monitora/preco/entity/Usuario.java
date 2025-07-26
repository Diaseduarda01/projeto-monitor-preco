package com.monitora.preco.entity;

import com.monitora.preco.utils.RoleConverter;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;
    private String email;
    private String senha;

    @Column(nullable = false)
    @Convert(converter = RoleConverter.class)
    private Role role;
}