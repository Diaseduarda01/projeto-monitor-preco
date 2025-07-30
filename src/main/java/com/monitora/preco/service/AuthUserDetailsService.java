package com.monitora.preco.service;

import com.monitora.preco.entity.Usuario;
import com.monitora.preco.exception.naoencontrado.UsuarioNaoEncontradoException;
import com.monitora.preco.repository.UsuarioRepository;
import com.monitora.preco.utils.LoggerUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthUserDetailsService implements UserDetailsService {

    private final UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = repository.findByEmail(email)
                .orElseThrow(() -> {
                    LoggerUtils.warn("Usuário não encontrado para o e-mail: " + email);
                    return new UsuarioNaoEncontradoException();
                });

        LoggerUtils.info("Usuário autenticado com sucesso: " + email);

        return new User(
                usuario.getEmail(),
                usuario.getSenha(),
                List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getRole().getNome()))
        );
    }
}