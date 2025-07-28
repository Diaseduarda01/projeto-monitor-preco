package com.monitora.preco.service;

import com.monitora.preco.entity.HistoricoPreco;
import com.monitora.preco.entity.Notificacao;
import com.monitora.preco.entity.Produto;
import com.monitora.preco.exception.PrecoNaoEncontradoException;
import com.monitora.preco.utils.LoggerUtils;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.AllArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
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


    public BigDecimal buscarPreco(String url, String classe) {
        WebDriverManager.chromedriver().browserVersion("135.0.7049.84").setup();
        WebDriver driver = new ChromeDriver();

        try {
            driver.get(url);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            List<WebElement> elementos;

            if (classe != null && !classe.isBlank()) {
                if (classe.contains(" ")) {
                    // Múltiplas classes: seletor CSS
                    String seletorCss = "." + classe.trim().replace(" ", ".");
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(seletorCss)));
                    elementos = driver.findElements(By.cssSelector(seletorCss));
                } else {
                    // Classe única
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(classe)));
                    elementos = driver.findElements(By.className(classe));
                }
            } else {
                // Busca genérica por qualquer elemento que contenha "R$"
                By xpath = By.xpath("//*[contains(text(),'R$')]");
                wait.until(ExpectedConditions.visibilityOfElementLocated(xpath));
                elementos = driver.findElements(xpath);
            }

            for (WebElement elemento : elementos) {
                // Garante que o elemento esteja visível e tenha texto
                if (elemento.isDisplayed()) {
                    String texto = elemento.getText();
                    if (texto.matches(".*R\\$\\s?\\d+[\\.,]?\\d*.*")) {
                        String precoStr = texto.replaceAll("[^\\d,\\.]", "")
                                .replace(".", "")
                                .replace(",", ".")
                                .trim();

                        return new BigDecimal(precoStr);
                    }
                }
            }

            throw new PrecoNaoEncontradoException();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar preço: " + e.getMessage(), e);
        } finally {
            driver.quit();
        }
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
                        "🎯 Preço atingido: " + produto.getNome(),
                        produto.getNome(),
                        "R$ " + precoAtual,
                        "R$ " + produto.getPrecoDesejado(),
                        produto.getUrl(),
                        produto.getUsuario().getNome()
                );

                notificacao.setEnviado(true);
                notificacaoService.salvar(notificacao, notificacao.getUsuario().getId(), notificacao.getProduto().getId());
            }
        }
    }
}
