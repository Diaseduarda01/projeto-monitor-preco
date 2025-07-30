    package com.monitora.preco.controller.usuario;

    import com.monitora.preco.dto.usuario.UsuarioMapper;
    import com.monitora.preco.dto.usuario.UsuarioRequestDto;
    import com.monitora.preco.dto.usuario.UsuarioResponseDto;
    import com.monitora.preco.entity.Usuario;
    import com.monitora.preco.service.UsuarioService;
    import lombok.RequiredArgsConstructor;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;

    @RestController
    @RequestMapping("/usuarios")
    @RequiredArgsConstructor
    public class UsuarioController implements UsuarioControllerDoc {

        private final UsuarioMapper mapper;
        private final UsuarioService service;

        @Override
        public ResponseEntity<UsuarioResponseDto> salvarUsuario(UsuarioRequestDto request) {
            Usuario usuarioSalvar = mapper.toEntity(request);
            String nomeRole = request.role();
            Usuario usuarioSalvo = service.salvar(usuarioSalvar, nomeRole);

            return ResponseEntity.status(201).body(mapper.toResponseDto(usuarioSalvo));
        }

        @Override
        public ResponseEntity<UsuarioResponseDto> buscarUsuarioPorId(Integer id) {
            Usuario usuario = service.buscarPorId(id);
            return ResponseEntity.status(201).body(mapper.toResponseDto(usuario));
        }

        @Override
        public ResponseEntity<List<UsuarioResponseDto>> buscarTodosUsuarios() {
            List<Usuario> usuarios = service.buscarTodos();

            return usuarios.isEmpty()
                    ? ResponseEntity.status(204).build()
                    : ResponseEntity.status(201).body(usuarios.stream()
                    .map(mapper::toResponseDto)
                    .toList());
        }

        @Override
        public ResponseEntity<UsuarioResponseDto> atualizarUsuario(UsuarioRequestDto request, Integer id) {
            Usuario usuarioSalvar = mapper.toEntity(request);
            Usuario usuarioSalvo = service.editar(id, usuarioSalvar);
            return ResponseEntity.status(201).body(mapper.toResponseDto(usuarioSalvo));
        }

        @Override
        public ResponseEntity<String> deletarUsuario(Integer id) {
            service.deletar(id);
            return  ResponseEntity.ok("Usuário deletado com sucesso.");
        }
    }