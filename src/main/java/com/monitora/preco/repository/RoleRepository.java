package com.monitora.preco.repository;

import com.monitora.preco.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByNomeIgnoreCase(String nome);
}
