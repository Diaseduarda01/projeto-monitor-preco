package com.monitora.preco.service;

import com.monitora.preco.entity.Role;
import com.monitora.preco.exception.naoencontrado.RoleNaoEncontradoException;
import com.monitora.preco.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoleService {

    private final RoleRepository repository;

    public Role buscarPorNome(String nome) {
        return repository.findByNomeIgnoreCase(nome)
                .orElseThrow(RoleNaoEncontradoException::new);
    }
}
