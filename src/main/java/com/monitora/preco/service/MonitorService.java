package com.monitora.preco.service;

import com.monitora.preco.entity.HistoricoPreco;
import com.monitora.preco.entity.Notificacao;
import com.monitora.preco.entity.Produto;
import com.monitora.preco.exception.ClasseNaoEncontradaException;
import com.monitora.preco.utils.LoggerUtils;
import lombok.AllArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
        LoggerUtils.info("🔍 Starting monitor preço");

        List<Produto> produtos = service.buscarProdutosAtivos();

        for (Produto produto : produtos) {
            try {
                BigDecimal precoAtual = buscarPreco(produto.getUrl(), produto.getClasse());

                if (precoAtual != null) {
                    salvarHistorico(produto, precoAtual);
                    verificarNotificacao(produto, precoAtual);

                LoggerUtils.logProduto("Preço monitorado", produto.getNome(), "Preço atual: R$" + precoAtual);
                }

            } catch (Exception e) {
                LoggerUtils.error("Error ao monitorar produto: " + produto.getNome(), e);
            }
        }

        LoggerUtils.info("Finish monitor preço");
    }

    private BigDecimal buscarPreco(String url, String classePreco) throws IOException {
        Document doc = Jsoup.connect(url)
                .timeout(10000)
                .userAgent("Mozilla/5.0")
                .get();

        String seletor = "span." + classePreco;

        Element precoElement = doc.selectFirst(seletor);

        if (precoElement == null) {
            throw new ClasseNaoEncontradaException();
        }

        String precoStr = precoElement.text();
        precoStr = precoStr.replace("R$", "")
                .replace(".", "")
                .replace(",", ".")
                .trim();

        return new BigDecimal(precoStr);
    }


    private void salvarHistorico(Produto produto, BigDecimal preco) {
        HistoricoPreco historico = new HistoricoPreco();
        historico.setProduto(produto);
        historico.setPrecoColetado(preco);

        historicoPrecoService.salvar(historico, historico.getProduto().getId());
    }

    private void verificarNotificacao(Produto produto, BigDecimal precoAtual) {
        if (precoAtual.compareTo(produto.getPrecoDesejado()) <= 0) {

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
                        "🎯 Preço atingido!",
                        "O produto '" + produto.getNome() + "' atingiu o preço de R$" + precoAtual
                );

                notificacao.setEnviado(true);
                notificacaoService.salvar(notificacao, notificacao.getUsuario().getId(), notificacao.getProduto().getId());
            }
        }
    }
}
