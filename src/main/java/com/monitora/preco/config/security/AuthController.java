package com.monitora.preco.config.security;

import com.monitora.preco.config.security.dto.AuthRequest;
import com.monitora.preco.config.security.dto.AuthResponse;
import com.monitora.preco.config.security.jwt.JwtUtil;
import com.monitora.preco.config.security.service.AuthUserDetailsService;
import com.monitora.preco.dto.usuario.UsuarioMapper;
import com.monitora.preco.dto.usuario.UsuarioRequestDto;
import com.monitora.preco.entity.Usuario;
import com.monitora.preco.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authManager;
    private final AuthUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final UsuarioService usuarioService;
    private final PasswordEncoder encoder;
    private final UsuarioMapper mapper;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getSenha()));
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = jwtUtil.gerarToken(userDetails);

        Usuario usuario = usuarioService.buscarPorEmail(request.getEmail());
        return ResponseEntity.ok(new AuthResponse(token, usuario.getNome(), usuario.getRole().name()));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody UsuarioRequestDto dto) {
        dto.setSenha(encoder.encode(dto.getSenha()));
        Usuario usuarioSalvar = mapper.toEntity(dto);
        Usuario novo = usuarioService.salvar(usuarioSalvar);

        String token = jwtUtil.gerarToken(
                new User(
                        novo.getEmail(),
                        novo.getSenha(),
                        List.of(new SimpleGrantedAuthority("ROLE_" + novo.getRole().name()))
                )
        );

        return ResponseEntity.ok(new AuthResponse(token, novo.getNome(), novo.getRole().name()));
    }

}
