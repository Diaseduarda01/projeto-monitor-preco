package com.monitora.preco.service;

import com.monitora.preco.entity.Role;
import com.monitora.preco.entity.Usuario;
import com.monitora.preco.exception.naoencontrado.UsuarioNaoEncontradoException;
import com.monitora.preco.repository.UsuarioRepository;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;
    private final RoleService roleService;

    public Usuario salvar(Usuario usuario, String nome){
       Role role = roleService.buscarPorNome(nome);

       usuario.setRole(role);

       return repository.save(usuario);
    }

    public Usuario buscarPorId(Integer id){
        return repository.findById(id).orElseThrow(UsuarioNaoEncontradoException::new);
    }

    public List<Usuario> buscarTodos(){
        return repository.findAll();
    }

    public Usuario editar(Integer id, Usuario usuarioAtualizado) {
        Usuario usuarioExistente = buscarPorId(id);
        atualizarCampos(usuarioExistente, usuarioAtualizado);

        String nomeRole = (usuarioAtualizado.getRole() != null)
                ? usuarioAtualizado.getRole().getNome()
                : usuarioExistente.getRole().getNome();

        return salvar(usuarioExistente, nomeRole);
    }

    private void atualizarCampos(Usuario destino, Usuario origem) {
        destino.setNome(origem.getNome());
        destino.setEmail(origem.getEmail());
        destino.setSenha(origem.getSenha());
    }
    public void deletar(Integer id){
        repository.deleteById(id);
    }

    public Usuario buscarPorEmail(@NotBlank String email) {
        return repository.findByEmail(email).orElseThrow(UsuarioNaoEncontradoException::new);
    }
}