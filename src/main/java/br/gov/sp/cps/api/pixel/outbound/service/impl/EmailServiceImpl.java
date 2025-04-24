package br.gov.sp.cps.api.pixel.outbound.service.impl;

import br.gov.sp.cps.api.pixel.core.domain.dto.command.EnviarEmailCommand;
import br.gov.sp.cps.api.pixel.outbound.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public void sendSimpleMail(EnviarEmailCommand command) {

        List<String> recipients = command.recipients();

        if (recipients == null || recipients.isEmpty()) {
            log.warn("Lista de destinatários está vazia");
            return;
        }

        for (String recipient : recipients) {
            try {
                if (recipient == null || recipient.isBlank()) {
                    log.warn("Destinatário inválido (em branco ou nulo)");
                    continue;
                }

                SimpleMailMessage mailMessage = new SimpleMailMessage();
                mailMessage.setFrom(sender);
                mailMessage.setTo(recipient);
                mailMessage.setText(command.msgBody());
                mailMessage.setSubject(command.subject());

                javaMailSender.send(mailMessage);
                log.info("Email enviado com sucesso para {}", recipient);
            } catch (Exception e) {
                log.error("Erro ao enviar email para {}: {}", recipient, e.getMessage(), e);
            }
        }
    }
}
