package com.monitora.preco.controller.produto;

import com.monitora.preco.dto.produto.ProdutoMapper;
import com.monitora.preco.dto.produto.ProdutoRequestDto;
import com.monitora.preco.dto.produto.ProdutoResponseDto;
import com.monitora.preco.entity.Produto;
import com.monitora.preco.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class ProdutoController implements ProdutoControllerDoc {

    private final ProdutoService service;
    private final ProdutoMapper mapper;

    @Override
    public ResponseEntity<ProdutoResponseDto> salvar(ProdutoRequestDto request) {
        Produto produtoSalvar = mapper.toEntity(request);
        Produto produtoSalvo = service.salvar(produtoSalvar, request.idUsuario());
        return ResponseEntity.status(201).body(mapper.toResponse(produtoSalvo));
    }

    @Override
    public ResponseEntity<ProdutoResponseDto> buscarPorId(Integer id) {
        Produto produtoBuscar = service.buscarPorId(id);

        return ResponseEntity.status(201).body(mapper.toResponse(produtoBuscar));
    }

    @Override
    public ResponseEntity<List<ProdutoResponseDto>> buscarTodos() {
        List<Produto> produtoList = service.buscarTodos();

        return produtoList.isEmpty()
                ? ResponseEntity.status(204).build()
                : ResponseEntity.status(201).body(produtoList.stream()
                .map(mapper::toResponse)
                .toList());
    }

    @Override
    public ResponseEntity<ProdutoResponseDto> atualizar(ProdutoRequestDto request, Integer id) {
        Produto produtoSalvar = mapper.toEntity(request);
        Produto produtoSalvo = service.atualizar(id, produtoSalvar);
        return ResponseEntity.status(201).body(mapper.toResponse(produtoSalvo));
    }

    @Override
    public ResponseEntity<String> deletar(Integer id) {
        service.deletar(id);
        return  ResponseEntity.ok("Produto deletado com sucesso.");
    }

}
