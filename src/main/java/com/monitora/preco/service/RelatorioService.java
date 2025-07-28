package com.monitora.preco.service;

import com.monitora.preco.dto.relatorio.RelatorioResponseDto;
import com.monitora.preco.entity.Relatorio;
import com.monitora.preco.entity.Usuario;
import com.monitora.preco.exception.naoencontrado.RelatorioNaoEncontradoException;
import com.monitora.preco.repository.RelatorioRepository;
import com.monitora.preco.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class RelatorioService {

    private final RelatorioRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioService usuarioService;

    public Relatorio gerarRelatorio(Integer usuarioId, String tipo) {
        Usuario usuario = usuarioService.buscarPorId(usuarioId);

        String nomeArquivo = "relatorio_" + tipo + "_" + System.currentTimeMillis() + ".pdf";
        String caminhoArquivo = "arquivos/" + nomeArquivo;


        Relatorio relatorio = new Relatorio();
        relatorio.setUsuario(usuario);
        relatorio.setTipo(tipo);
        relatorio.setCaminhoArquivo(caminhoArquivo);
        relatorio.setDataGeracao(LocalDateTime.now());

        return repository.save(relatorio);
    }

    public List<RelatorioResponseDto> listarRelatorios(Integer usuarioId) {
        return repository.findByUsuarioId(usuarioId).stream()
                .map(r -> new RelatorioResponseDto(
                        r.getId(),
                        r.getTipo(),
                        r.getCaminhoArquivo(),
                        r.getDataGeracao()
                ))
                .toList();
    }

    public FileSystemResource downloadRelatorio(Integer relatorioId) {
        Relatorio relatorio = repository.findById(relatorioId)
                .orElseThrow(RelatorioNaoEncontradoException::new);

        File file = new File(relatorio.getCaminhoArquivo());

        if (!file.exists()) {
            throw new RuntimeException("Arquivo não encontrado no caminho: " + relatorio.getCaminhoArquivo());
        }

        return new FileSystemResource(file);
    }
}
