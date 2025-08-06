package com.monitora.preco.service;

import com.monitora.preco.entity.HistoricoPreco;
import com.monitora.preco.entity.Notificacao;
import com.monitora.preco.entity.Produto;
import com.monitora.preco.exception.naoencontrado.PrecoNaoEncontradoException;
import com.monitora.preco.utils.LoggerUtils;
import lombok.AllArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class MonitorService {

    private final ProdutoService service;
    private final HistoricoPrecoService historicoPrecoService;
    private final NotificacaoService notificacaoService;
    private final EmailService emailService;

    @Scheduled(fixedRateString = "3600000")
    public void executarMonitoramento() {

        List<Produto> produtos = service.buscarProdutosAtivos();
        LoggerUtils.info("Produtos ativos encontrados: " + produtos.size());

        for (Produto produto : produtos) {
            try {

                BigDecimal precoAtual = buscarPreco(produto.getUrl(), produto.getClasse());

                if (precoAtual != null) {
                    LoggerUtils.logProduto("PREÇO COLETADO", produto.getNome(), "R$ " + precoAtual);

                    salvarHistorico(produto, precoAtual);
                    verificarNotificacao(produto, precoAtual);
                }

            } catch (Exception e) {
                LoggerUtils.error("Erro ao monitorar o produto: " + produto.getNome(), e);
            }
        }
    }

    public BigDecimal buscarPreco(String url, String classe) {
        try {
            // Faz o GET com user-agent para evitar bloqueio
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0")
                    .timeout(15000)
                    .get();

            Elements elementos;

            if (classe != null && !classe.isBlank()) {
                if (classe.contains(" ")) {
                    String seletorCss = "." + classe.trim().replace(" ", ".");
                    elementos = doc.select(seletorCss);
                } else {
                    elementos = doc.getElementsByClass(classe);
                }
            } else {
                elementos = doc.getElementsContainingOwnText("R$");
            }

            for (Element elemento : elementos) {
                String texto = elemento.text();
                if (texto.matches(".*R\\$\\s?\\d+[\\.,]?\\d*.*")) {
                    String precoStr = texto.replaceAll("[^\\d,\\.]", "")
                            .replace(".", "")
                            .replace(",", ".")
                            .trim();

                    LoggerUtils.info("Preço extraído com sucesso: " + precoStr);
                    return new BigDecimal(precoStr);
                }
            }

            LoggerUtils.error("Preço não encontrado na página");
            throw new PrecoNaoEncontradoException();

        } catch (Exception e) {
            LoggerUtils.error("Erro ao buscar preço na URL: " + url, e);
            throw new RuntimeException("Erro ao buscar preço: " + e.getMessage(), e);
        }
    }


    private void salvarHistorico(Produto produto, BigDecimal preco) {
        HistoricoPreco historico = new HistoricoPreco();
        historico.setProduto(produto);
        historico.setPrecoColetado(preco);

        historicoPrecoService.salvar(historico, historico.getProduto().getId());
        LoggerUtils.info("Histórico salvo");
    }

    private void verificarNotificacao(Produto produto, BigDecimal precoAtual) {

        if (precoAtual.compareTo(produto.getPrecoDesejado()) <= 0) {
            LoggerUtils.info("Preço atual (R$ " + precoAtual + ") atingiu ou está abaixo do preço desejado (R$ " + produto.getPrecoDesejado() + ")");

            boolean jaNotificou = notificacaoService.jaNotificou(produto, precoAtual);

            if (!jaNotificou) {
                Notificacao notificacao = new Notificacao();
                notificacao.setProduto(produto);
                notificacao.setUsuario(produto.getUsuario());
                notificacao.setPrecoAlvo(produto.getPrecoDesejado());
                notificacao.setPrecoAtingido(precoAtual);
                notificacao.setEnviado(false);

                notificacaoService.salvar(notificacao, notificacao.getUsuario().getId(), notificacao.getProduto().getId());

                emailService.enviarEmail(
                        produto.getUsuario().getEmail(),
                        "🎯 Preço atingido: " + produto.getNome(),
                        produto.getNome(),
                        "R$ " + precoAtual,
                        "R$ " + produto.getPrecoDesejado(),
                        produto.getUrl(),
                        produto.getUsuario().getNome()
                );

                notificacao.setEnviado(true);
                notificacaoService.salvar(notificacao, notificacao.getUsuario().getId(), notificacao.getProduto().getId());

                LoggerUtils.info("Notificação enviada!");
            } else {
                LoggerUtils.info("Notificação já enviada anteriormente para esse preço.");
            }
        } else {
            LoggerUtils.info("Preço ainda não atingiu o desejado. Nenhuma ação necessária.");
        }
    }
}