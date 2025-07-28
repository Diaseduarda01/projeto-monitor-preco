package com.monitora.preco.controller;

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
public class UsuarioController {

    private final UsuarioMapper mapper;
    private final UsuarioService service;

    @PostMapping
    public ResponseEntity<UsuarioResponseDto> salvar(@RequestBody UsuarioRequestDto request){
        Usuario usuarioSalvar = mapper.toEntity(request);
        String nomeRole = request.role().nome();
        Usuario usuarioSalvo = service.salvar(usuarioSalvar, nomeRole);

        return ResponseEntity.status(201).body(mapper.toResponseDto(usuarioSalvo));
    }


    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> buscarporId(@PathVariable Integer id){
        Usuario usuario = service.buscarPorId(id);
        return ResponseEntity.status(201).body(mapper.toResponseDto(usuario));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDto>> buscarTodos(){
        List<Usuario> usuarios = service.buscarTodos();

        return usuarios.isEmpty()
                ? ResponseEntity.status(204).build()
                : ResponseEntity.status(201).body(usuarios.stream()
                .map(mapper::toResponseDto)
                .toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> editar(@RequestBody UsuarioRequestDto request, @PathVariable Integer id){
        Usuario usuarioSalvar = mapper.toEntity(request);
        Usuario usuarioSalvo = service.editar(id, usuarioSalvar);
        return ResponseEntity.status(201).body(mapper.toResponseDto(usuarioSalvo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable Integer id){
        service.deletar(id);
        return  ResponseEntity.ok("Usuário deletado com sucesso.");
    }
}