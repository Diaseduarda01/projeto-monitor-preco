package com.monitora.preco.controller.auth;

import com.monitora.preco.dto.auth.AuthRequestDto;
import com.monitora.preco.dto.auth.AuthResponseDto;
import com.monitora.preco.dto.usuario.UsuarioMapper;
import com.monitora.preco.dto.usuario.UsuarioRequestDto;
import com.monitora.preco.entity.Usuario;
import com.monitora.preco.service.UsuarioService;
import com.monitora.preco.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController implements AuthControllerDoc{

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final UsuarioService usuarioService;
    private final PasswordEncoder encoder;
    private final UsuarioMapper mapper;

    @Override
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto request) {
        autenticar(request);

        Usuario usuario = usuarioService.buscarPorEmail(request.email());
        String token = gerarToken(usuario);

        var roleDto = new AuthResponseDto.RoleResponseDto(
                usuario.getRole().getId(),
                usuario.getRole().getNome()
        );

        return ResponseEntity.ok(new AuthResponseDto(token, usuario.getNome(), usuario.getId(), roleDto));
    }


  @Override
    public ResponseEntity<AuthResponseDto> register(@RequestBody UsuarioRequestDto dto) {
        Usuario usuarioSalvar = prepararUsuarioParaSalvar(dto);
        Usuario novo = usuarioService.salvar(usuarioSalvar, dto.role());
        String token = gerarToken(novo);

        var roleDto = new AuthResponseDto.RoleResponseDto(
                novo.getRole().getId(),
                novo.getRole().getNome()
        );

        return ResponseEntity.ok(new AuthResponseDto(token, novo.getNome(), novo.getId(), roleDto));
    }


    // ========== MÉTODOS PRIVADOS AUXILIARES ==========

    private void autenticar(AuthRequestDto request) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.email(),
                request.senha()
        ));
    }

    private String gerarToken(Usuario usuario) {
        UserDetails userDetails = new User(
                usuario.getEmail(),
                usuario.getNome(),
                List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getRole().getNome()))
        );
        return jwtUtil.gerarToken(userDetails);
    }

    private Usuario prepararUsuarioParaSalvar(UsuarioRequestDto dto) {
        Usuario usuario = mapper.toEntity(dto);
        usuario.setSenha(encoder.encode(dto.senha()));
        return usuario;
    }
}