package com.monitora.preco.controller.produto;

import com.monitora.preco.dto.produto.ProdutoRequestDto;
import com.monitora.preco.dto.produto.ProdutoResponseDto;
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
@Tag(name = "Produtos", description = "Operações relacionadas aos produtos")
public interface ProdutoControllerDoc {

    @PostMapping()
    @Operation(summary = "Salvar produto", description = """
            Salvar produto
            ---
            Salva produto no banco de dados
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Quando o produto é cadastrada com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProdutoResponseDto.class)
                    )),
            @ApiResponse(responseCode = "400", description = "Quando o corpo de requisição está incorreto",
                    content = @Content()),
            @ApiResponse(responseCode = "404", description = "Quando o id do usuário recebido no corpo não existe no banco de dados",
                    content = @Content())
    })
    ResponseEntity<ProdutoResponseDto> salvar(@RequestBody ProdutoRequestDto request);

    @GetMapping("/{id}")
    @Operation(summary = "Buscar produto por id", description = """
           Buscar produto Por id
            ---
           Buscar produto Por id no banco de dados
           """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quando o produto é encontrado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProdutoResponseDto.class)
                    )),
            @ApiResponse(responseCode = "404", description = "Quando o produto não for encontrado pelo id no banco de dados",
                    content = @Content())
    })
    ResponseEntity<ProdutoResponseDto> buscarPorId(@PathVariable Integer id);


    @GetMapping()
    @Operation(summary = "Buscar todos os produtos", description = """
           Buscar todos os produtos
            ---
           Buscar todos os produtos que estão cadastrados no banco de dados
           """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quando existe produto cadastrado no banco de dados",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProdutoResponseDto.class)
                    )),
            @ApiResponse(responseCode = "204", description = "Quando não há nenhum produto cadastrado no banco de dados",
                    content = @Content())
    })
    ResponseEntity<List<ProdutoResponseDto>> buscarTodos();

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar produto", description = """
           Atualizar produto
            ---
           Atualizar produto no banco de dados
           """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quando produto foi atualizado com sucesso no banco de dados",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProdutoResponseDto.class)
                    )),
            @ApiResponse(responseCode = "400", description = "Quando o corpo de requisição está incorreto",
                    content = @Content()),
            @ApiResponse(responseCode = "404", description = "Quando o id do usuário recebido no corpo não existe no banco de dados",
                    content = @Content())
    })
    ResponseEntity<ProdutoResponseDto> atualizar(@RequestBody ProdutoRequestDto request, @PathVariable Integer id);

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar produto por id", description = """
        Deleta um produto no banco de dados com base no id fornecido.
        """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto deletado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = String.class)
                    )),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado com o ID fornecido",
                    content = @Content())
    })
    ResponseEntity<String> deletar(@PathVariable Integer id);
}
