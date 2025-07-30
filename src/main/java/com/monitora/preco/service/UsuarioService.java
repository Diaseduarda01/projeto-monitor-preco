package com.monitora.preco.service;

import com.monitora.preco.entity.Role;
import com.monitora.preco.entity.Usuario;
import com.monitora.preco.exception.naoencontrado.UsuarioNaoEncontradoException;
import com.monitora.preco.repository.UsuarioRepository;
import com.monitora.preco.utils.LoggerUtils;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;
    private final RoleService roleService;

    public Usuario salvar(Usuario usuario, String nome) {
        Role role = roleService.buscarPorNome(nome);
        usuario.setRole(role);
        Usuario salvo = repository.save(usuario);
        LoggerUtils.info( "Usuário salvo com ID: " + salvo.getId());
        return salvo;
    }

    public Usuario buscarPorId(Integer id) {
        return repository.findById(id).orElseThrow(() -> {
            LoggerUtils.error("Usuário com ID " + id + " não encontrado");
            return new UsuarioNaoEncontradoException();
        });
    }

    public List<Usuario> buscarTodos() {
        List<Usuario> lista = repository.findAll();
        LoggerUtils.info("Total de usuários encontrados: " + lista.size());
        return lista;
    }

    public Usuario editar(Integer id, Usuario usuarioAtualizado) {
        Usuario usuarioExistente = buscarPorId(id);
        atualizarCampos(usuarioExistente, usuarioAtualizado);

        String nomeRole = (usuarioAtualizado.getRole() != null)
                ? usuarioAtualizado.getRole().getNome()
                : usuarioExistente.getRole().getNome();

        Usuario atualizado = salvar(usuarioExistente, nomeRole);
        LoggerUtils.info("Usuário atualizado com sucesso");
        return atualizado;
    }

    private void atualizarCampos(Usuario destino, Usuario origem) {
        destino.setNome(origem.getNome());
        destino.setEmail(origem.getEmail());
        destino.setSenha(origem.getSenha());
    }

    public void deletar(Integer id) {
        repository.deleteById(id);
        LoggerUtils.info( "Usuário deletado com sucesso");
    }

    public Usuario buscarPorEmail(@NotBlank String email) {
        return repository.findByEmail(email).orElseThrow(() -> {
            LoggerUtils.error("Usuário com e-mail " + email + " não encontrado");
            return new UsuarioNaoEncontradoException();
        });
    }
}