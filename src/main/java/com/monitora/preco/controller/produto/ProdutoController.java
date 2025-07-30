package com.monitora.preco.controller;

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
public class ProdutoController {

    private final ProdutoService service;
    private final ProdutoMapper mapper;

    @PostMapping
    public ResponseEntity<ProdutoResponseDto> salvar(@RequestBody ProdutoRequestDto dto) {
        Produto produtoSalvar = mapper.toEntity(dto);
        Produto produtoSalvo = service.salvar(produtoSalvar, dto.idUsuario());
        return ResponseEntity.status(201).body(mapper.toResponse(produtoSalvo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDto> buscarPorId(@PathVariable Integer id) {
        Produto produtoBuscar = service.buscarPorId(id);

        return ResponseEntity.status(201).body(mapper.toResponse(produtoBuscar));
    }

    @GetMapping
    public ResponseEntity<List<ProdutoResponseDto>> buscarTodos(){
        List<Produto> produtoList = service.buscarTodos();

        return produtoList.isEmpty()
                ? ResponseEntity.status(204).build()
                : ResponseEntity.status(201).body(produtoList.stream()
                .map(mapper::toResponse)
                .toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDto> editar(@RequestBody ProdutoRequestDto request, @PathVariable Integer id){
        Produto produtoSalvar = mapper.toEntity(request);
        Produto produtoSalvo = service.atualizar(id, produtoSalvar);
        return ResponseEntity.status(201).body(mapper.toResponse(produtoSalvo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable Integer id){
        service.deletar(id);
        return  ResponseEntity.ok("produto deletado com sucesso.");
    }

}
