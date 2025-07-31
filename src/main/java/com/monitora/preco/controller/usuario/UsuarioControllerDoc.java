package com.monitora.preco.controller.usuario;

import com.monitora.preco.dto.usuario.UsuarioRequestDto;
import com.monitora.preco.dto.usuario.UsuarioResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Usuários", description = "Operações relacionadas aos usuários")
public interface UsuarioControllerDoc {

    @PostMapping()
    @Operation(summary = "Salvar usuário", description = """
            Salvar usuário
            ---
            Salva usuário no banco de dados
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Quando o usuário é cadastrada com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UsuarioResponseDto.class)
                    )),
            @ApiResponse(responseCode = "400", description = "Quando o corpo de requisição está incorreto",
                    content = @Content()),
            @ApiResponse(responseCode = "404", description = "Quando o nome do role recebido no corpo não existe no banco de dados",
                    content = @Content())
    })
    ResponseEntity<UsuarioResponseDto> salvar(@RequestBody UsuarioRequestDto request);

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por id", description = """
           Buscar usuário por id
            ---
           Buscar usuário por id no banco de dados
           """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quando o usuário é encontrado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UsuarioResponseDto.class)
                    )),
            @ApiResponse(responseCode = "404", description = "Quando o usuaŕio não for encontrado pelo id no banco de dados",
                    content = @Content())
    })
    ResponseEntity<UsuarioResponseDto> buscarPorId(@PathVariable Integer id);


    @GetMapping()
    @Operation(summary = "Buscar todos os usuário", description = """
           Buscar todos os usuário
            ---
           Buscar todos os usuário que estão cadastrados no banco de dados
           """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quando existe usuário cadastrado no banco de dados",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UsuarioResponseDto.class)
                    )),
            @ApiResponse(responseCode = "204", description = "Quando não há nenhum usuaŕio cadastrado no banco de dados",
                    content = @Content())
    })
    ResponseEntity<List<UsuarioResponseDto>> buscarTodos();

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar usuário", description = """
           Atualizar usuário
            ---
           Atualizar usuário no banco de dados
           """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quando usuário foi atualizado com sucesso no banco de dados",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UsuarioResponseDto.class)
                    )),
            @ApiResponse(responseCode = "400", description = "Quando o corpo de requisição está incorreto",
                    content = @Content()),
            @ApiResponse(responseCode = "404", description = "Quando o nome do role recebido no corpo não existe no banco de dados",
                    content = @Content())
    })
    ResponseEntity<UsuarioResponseDto> atualizar(@RequestBody UsuarioRequestDto request, @PathVariable Integer id);

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar usuário por id", description = """
        Deleta um usuário no banco de dados com base no id fornecido.
        """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário deletado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = String.class)
                    )),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado com o ID fornecido",
                    content = @Content())
    })
    ResponseEntity<String> deletar(@PathVariable Integer id);
}
