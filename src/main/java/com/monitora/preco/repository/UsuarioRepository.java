package com.monitora.preco.repository;

import com.monitora.preco.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {


  Optional<Usuario> findByEmail(String email);
}
