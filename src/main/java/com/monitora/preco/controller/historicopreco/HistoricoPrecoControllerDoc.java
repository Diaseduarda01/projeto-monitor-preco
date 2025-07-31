package com.monitora.preco.controller.historicopreco;

import com.monitora.preco.dto.dashboard.ProdutoDashboardResponseDto;
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

import java.math.BigDecimal;
import java.util.List;

@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Histórico Preço", description = "Operações relacionadas a históricos preços")
public interface HistoricoPrecoControllerDoc {

    @GetMapping("/listar-historico/{produtoId}/{usuarioId}")
    @Operation(
            summary = "Listar histórico de preços",
            description = "Retorna o histórico de preços de um produto monitorado por um usuário específico, considerando apenas registros marcados como 'ativos' no banco de dados."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quando existe histórico de preços no banco de dados para o produto e usuário informados",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HistoricoPrecoResponseDto.class)
                    )),
            @ApiResponse(responseCode = "204", description = "Quando não existe histórico de preços para o produto e usuário informados",
                    content = @Content()),
            @ApiResponse(responseCode = "400", description = "Quando os parâmetros da requisição estão incorretos",
                    content = @Content())
    })
    ResponseEntity<List<HistoricoPrecoResponseDto>> listarHistoricoPorIdProduto(
            @PathVariable Integer produtoId,
            @PathVariable Integer usuarioId);

    @GetMapping("/listar-produtos/{usuarioId}")
    @Operation(
            summary = "Listar produtos",
            description = "Retorna os produtos monitorados por um usuário específico."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quando existe produto no banco de dados para" +
                    " o usuário informados",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HistoricoPrecoResponseDto.class)
                    )),
            @ApiResponse(responseCode = "204", description = "Quando não existe produto para o usuário informados",
                    content = @Content()),
            @ApiResponse(responseCode = "400", description = "Quando os parâmetros da requisição estão incorretos",
                    content = @Content())
    })
    ResponseEntity<List<ProdutoDashboardResponseDto>> listarProdutosParaDashboard(@PathVariable Integer usuarioId);

    @GetMapping("/total-monitorados/{usuarioId}")
    @Operation(
            summary = "Total de produtos monitorados",
            description = "Retorna o total de produtos monitorados cadastrados como 'ativos' para um usuário específico" +
                    " no banco de dados."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quando existem produtos cadastrados no banco de dados",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(type = "integer", format = "int32", example = "5")
                    )),
            @ApiResponse(responseCode = "204", description = "Quando não existem produtos cadastrados no banco de dados",
                    content = @Content()),
            @ApiResponse(responseCode = "400", description = "Quando o corpo da requisição está incorreto",
                    content = @Content())
    })
    ResponseEntity<Integer> totalMonitorados(@PathVariable Integer usuarioId);

    @GetMapping("/total-proximos-preco-desejado/{usuarioId}")
    @Operation(
            summary = "Total de produtos próximos ao preço desejado",
            description = "Retorna o total de produtos próximos ao preço desejado do banco de dados."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quando existem produtos próximos ao preço desejado no" +
                    " banco de dados",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(type = "integer", format = "int32", example = "5")
                    )),
            @ApiResponse(responseCode = "204", description = "Quando não existem produtos próximos ao preço desejado" +
                    " cadastrados no banco de dados",
                    content = @Content()),
            @ApiResponse(responseCode = "400", description = "Quando o corpo da requisição está incorreto",
                    content = @Content())
    })
    ResponseEntity<Integer> produtosProximosPrecoDesejado(@PathVariable Integer usuarioId);


    @GetMapping("/total-no-preco-desejado/{usuarioId}")
    @Operation(
            summary = "Total de produtos próximos ao preço desejado",
            description = "Retorna o total de produtos próximos ao preço desejado do banco de dados."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quando existem produtos próximos ao preço desejado no" +
                    " banco de dados",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(type = "integer", format = "int32", example = "5")
                    )),
            @ApiResponse(responseCode = "204", description = "Quando não existem produtos próximos ao preço desejado" +
                    " cadastrados no banco de dados",
                    content = @Content()),
            @ApiResponse(responseCode = "400", description = "Quando o corpo da requisição está incorreto",
                    content = @Content())
    })
    ResponseEntity<Integer> produtosNoOuAbaixoPrecoDesejado(@PathVariable Integer usuarioId);

    @GetMapping("/total-dias-monitoramento/{produtoId}/{usuarioId}")
    @Operation(
            summary = "Total de dias monitorando produtos",
            description = "Retorna o total de dias monitorando produtos do banco de dados."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quando existem produtos sendo monitarado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(type = "integer", format = "int32", example = "5")
                    )),
            @ApiResponse(responseCode = "204", description = "Quando não existem produtos sendo monitarado " +
                    "cadastrados no banco de dados",
                    content = @Content()),
            @ApiResponse(responseCode = "400", description = "Quando o corpo da requisição está incorreto",
                    content = @Content())
    })
    ResponseEntity<Long> getTempoMonitorado(@PathVariable Integer produtoId, @PathVariable Integer usuarioId);

    @GetMapping("/preco-desejado/{produtoId}/{usuarioId}")
    @Operation(
            summary = "Preço desejado de um produto específico",
            description = "Retorna o preço desejado de um produto específico do banco de dados."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quando existem produto com seu preço desejado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(type = "Double", format = "int32", example = "5")
                    )),
            @ApiResponse(responseCode = "204", description = "Quando não existem  produto com seu preço desejado " +
                    "cadastrado no banco de dados",
                    content = @Content()),
            @ApiResponse(responseCode = "400", description = "Quando o corpo da requisição está incorreto",
                    content = @Content())
    })
    ResponseEntity<BigDecimal> getPrecoDesejado(@PathVariable Integer produtoId, @PathVariable Integer usuarioId);

    @GetMapping("/preco-atual/{produtoId}/{usuarioId}")
    @Operation(
            summary = "Preço atual de um produto específico",
            description = "Retorna o preço atual de um produto específico do banco de dados."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quando existem preço atual de um produto específico",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(type = "Double", format = "int32", example = "5")
                    )),
            @ApiResponse(responseCode = "204", description = "Quando não existem preço atual de um produto específico" +
                    "cadastrado no banco de dados",
                    content = @Content()),
            @ApiResponse(responseCode = "400", description = "Quando o corpo da requisição está incorreto",
                    content = @Content())
    })
    ResponseEntity<BigDecimal> getUltimoPreco(@PathVariable Integer produtoId, @PathVariable Integer usuarioId);

    @GetMapping("/variacao-porcentual/{produtoId}/{usuarioId}")
    @Operation(
            summary = "Variação do preço de um produto específico",
            description = "Retorna a variação do preço de um produto específico do banco de dados."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quando existem variação do preço de um produto específico no" +
                    " de dados",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(type = "Double", format = "int32", example = "5")
                    )),
            @ApiResponse(responseCode = "204", description = "Quando não variação do preço de um produto específico " +
                    "cadastrado no banco de dados",
                    content = @Content()),
            @ApiResponse(responseCode = "400", description = "Quando o corpo da requisição está incorreto",
                    content = @Content())
    })
    ResponseEntity<BigDecimal> getVariacao(@PathVariable Integer produtoId, @PathVariable Integer usuarioId);
}
