package com.monitora.preco.controller.monitorpreco;

import com.monitora.preco.dto.usuario.UsuarioResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Monitor preço", description = "Operação relacionada a monitorar preços")
public interface MonitorPrecoControllerDoc {

    @PostMapping("/executar")
    @Operation(summary = "Executar monitoramento manual", description = """
        Executa manualmente o monitoramento dos produtos ativos cadastrados no banco de dados.
        """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Monitoramento manual executado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = String.class)
                    )),
            @ApiResponse(responseCode = "400", description = "Corpo da requisição inválido",
                    content = @Content())
    })
    ResponseEntity<String> monitorarAgora();
}
