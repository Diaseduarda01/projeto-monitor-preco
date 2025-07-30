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
    @Operation(summary = "Salvar Usuário", description = """
            Salvar Usuário
            ---
            Salva Usuário no banco de dados
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Quando a entidade é cadastrada com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UsuarioResponseDto.class)
                    )),
            @ApiResponse(responseCode = "400", description = "Quando o corpo de requisição está incorreto",
                    content = @Content()),
            @ApiResponse(responseCode = "404", description = "Quando o nome do role recebido no corpo não existe no banco de dados",
                    content = @Content())
    })
    ResponseEntity<UsuarioResponseDto> salvarUsuario(@RequestBody UsuarioRequestDto request);

    @GetMapping("/{id}")
    @Operation(summary = "Buscar Usuário Por Id", description = """
           Buscar Usuário Por Id
            ---
           Buscar Usuário Por Id no banco de dados
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
    ResponseEntity<UsuarioResponseDto> buscarUsuarioPorId(@PathVariable Integer id);


    @GetMapping()
    @Operation(summary = "Buscar Todos os Usuário", description = """
           Buscar Todos os Usuário 
            ---
           Buscar Todos os Usuário que estão cadastrados no banco de dados
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
    ResponseEntity<List<UsuarioResponseDto>> buscarTodosUsuarios();

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar Usuário", description = """
           Atualizar Usuário
            ---
           Atualizar Usuário no banco de dados
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
    ResponseEntity<UsuarioResponseDto> atualizarUsuario(@RequestBody UsuarioRequestDto request, @PathVariable Integer id);

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar Usuário por Id", description = """
           Deletar Usuário por Id
            ---
           Deletar Usuário por Id no banco de dados
           """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quando o usuário foi deletado no banco de dados com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UsuarioResponseDto.class)
                    )),
            @ApiResponse(responseCode = "204", description = "Quando não há nenhum usuaŕio cadastrado no banco de dados com este id",
                    content = @Content())
    })
    ResponseEntity<String> deletarUsuario(@PathVariable Integer id);
}
