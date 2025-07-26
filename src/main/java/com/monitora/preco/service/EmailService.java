package com.monitora.preco.service;

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

    public void enviarEmail(String para, String assunto, String corpo) {
        MimeMessage mensagem = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensagem);

        try {
            helper.setFrom("monitoraplicacao@gmail.com");
            helper.setTo(para);
            helper.setSubject(assunto);
            helper.setText(corpo);
            mailSender.send(mensagem);
            System.out.println("E-mail enviado com sucesso!");
        } catch (MailException | MessagingException e) {
            e.printStackTrace();
            System.out.println("Falha ao enviar e-mail.");
        }
    }
}

