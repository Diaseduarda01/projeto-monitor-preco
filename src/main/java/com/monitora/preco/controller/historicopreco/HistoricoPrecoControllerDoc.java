package com.monitora.preco.controller.historicopreco;

import com.monitora.preco.dto.historicopreco.HistoricoPrecoResponseDto;
import com.monitora.preco.dto.usuario.UsuarioResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Histórico Preço", description = "Operações relacionadas a históricos preços")
public interface HistoricoPrecoControllerDoc {

    @GetMapping("/{produtoId}/{usuarioId}")
    @Operation(summary = "Listar histórico de preços por id produto e por id usuário", description = """
            Listar histórico de preços por id produto e por id usuário
            ---
            Listar histórico de preços por id produto e por id usuário cadastrados como "ativo" no banco de dados
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quando o existe histórico de preço por id no banco de dados",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UsuarioResponseDto.class)
                    )),
            @ApiResponse(responseCode = "400", description = "Quando o corpo de requisição está incorreto",
                    content = @Content()),
    })
    ResponseEntity<List<HistoricoPrecoResponseDto>> listarHistoricoPorIdProduto(
            @PathVariable Integer produtoId,
            @PathVariable Integer usuarioId);


}
