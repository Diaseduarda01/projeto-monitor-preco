package com.monitora.preco.controller.auth;

import com.monitora.preco.dto.auth.AuthRequestDto;
import com.monitora.preco.dto.auth.AuthResponseDto;
import com.monitora.preco.dto.usuario.UsuarioRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Authentication", description = "Operações relacionadas a autentificação de usuários")
public interface AuthControllerDoc {

    @PostMapping("/register")
    @Operation(summary = "Registro de usuário", description = """
            Registro de usuário
            ---
            Registro de usuário no banco de dados
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Quando o usuário é registrado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AuthResponseDto.class)
                    )),
            @ApiResponse(responseCode = "400", description = "Quando o corpo de requisição está incorreto",
                    content = @Content())
    })
    ResponseEntity<AuthResponseDto> register(@RequestBody UsuarioRequestDto dto);

    @PostMapping("/login")
    @Operation(summary = "Autentificação de usuário", description = """
            Autentificação de usuário
            ---
            Autentificação de usuário no banco de dados
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Quando o usuário é autenticado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AuthResponseDto.class)
                    )),
            @ApiResponse(responseCode = "401", description = "Quando o corpo de requisição está incorreto",
                    content = @Content())
    })
    ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto request);
}
