package com.monitora.preco.service;

import com.monitora.preco.entity.Usuario;
import com.monitora.preco.exception.UsuarioNaoEncontradoException;
import com.monitora.preco.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;

    public Usuario salvar(Usuario usuario){
        return repository.save(usuario);
    }

    public Usuario buscarPorId(Integer id){
        return repository.findById(id).orElseThrow(UsuarioNaoEncontradoException::new);
    }

    public List<Usuario> buscarTodos(){
        return repository.findAll();
    }

    public Usuario editar(Integer id, Usuario usuarioAtualizado){
        Usuario usuarioExiste = buscarPorId(id);

        usuarioExiste.setNome(usuarioAtualizado.getNome());
        usuarioExiste.setEmail(usuarioAtualizado.getEmail());
        usuarioExiste.setSenha(usuarioAtualizado.getSenha());

        return salvar(usuarioExiste);
    }

    public void deletar(Integer id){
        Usuario usuario = buscarPorId(id);
        repository.delete(usuario);
    }
}