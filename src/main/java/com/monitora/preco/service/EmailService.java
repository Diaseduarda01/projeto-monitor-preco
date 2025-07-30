package com.monitora.preco.service;

import com.monitora.preco.utils.LoggerUtils;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void enviarEmail(String para, String assunto, String nomeProduto, String precoAtual, String precoDesejado, String linkProduto, String nomeUsuario) {
        MimeMessage mensagem = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mensagem, true, "UTF-8");
            helper.setFrom("monitoraplicacao@gmail.com");
            helper.setTo(para);
            helper.setSubject(assunto);

            String corpoHtml = """
                <html>
                <head>
                    <style>
                        body {
                            font-family: Arial, sans-serif;
                            background-color: #f4f4f8;
                            margin: 0;
                            padding: 0;
                        }
                        .container {
                            max-width: 600px;
                            margin: 40px auto;
                            background-color: #ffffff;
                            border-radius: 10px;
                            padding: 30px;
                            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
                            color: #333333;
                        }
                        .titulo {
                            font-size: 24px;
                            font-weight: bold;
                            color: #8648cc;
                            margin-bottom: 20px;
                        }
                        .info {
                            font-size: 16px;
                            margin-bottom: 20px;
                        }
                        .botao {
                            display: inline-block;
                            background-color: #8648cc;
                            color: white;
                            padding: 12px 24px;
                            border-radius: 6px;
                            text-decoration: none;
                            font-weight: bold;
                            transition: background-color 0.3s ease;
                        }
                        .botao:hover {
                            background-color: #6f38a9;
                        }
                        .footer {
                            margin-top: 40px;
                            font-size: 12px;
                            color: #888888;
                            text-align: center;
                        }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <div class="titulo">Produto em Promoção!</div>
                        <div class="info">
                            Olá <strong>%s</strong>,<br><br>
                            O produto <strong>%s</strong> está com preço igual ou abaixo do desejado.<br><br>
                            <strong>Preço atual:</strong> %s<br>
                            <strong>Preço desejado:</strong> %s
                        </div>
                        <a class="botao" href="%s" target="_blank">Ver produto agora</a>
                        <div class="footer">
                            Este é um e-mail automático enviado pelo sistema Monitora Preço.
                        </div>
                    </div>
                </body>
                </html>
                """.formatted(nomeUsuario, nomeProduto, precoAtual, precoDesejado, linkProduto);

            helper.setText(corpoHtml, true);
            mailSender.send(mensagem);

            LoggerUtils.info("E-mail enviado com sucesso para: " + para);
        } catch (MailException | MessagingException e) {
            LoggerUtils.error("Falha ao enviar e-mail para: " + para + ". Erro: " + e.getMessage());
        }
    }
}