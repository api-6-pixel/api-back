package br.gov.sp.cps.api.pixel.outbound.service.impl;

import br.gov.sp.cps.api.pixel.core.domain.dto.command.EnviarEmailCommand;
import br.gov.sp.cps.api.pixel.outbound.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public void sendSimpleMail(EnviarEmailCommand command) {
        try {
            if (command.recipient() == null || command.recipient().isBlank()) {
                log.warn("Destinatário do e-mail está vazio");
                return;
            }

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(command.recipient());
            mailMessage.setText(command.msgBody());
            mailMessage.setSubject(command.subject());

            javaMailSender.send(mailMessage);
            log.info("Email enviado com sucesso para {}", command.recipient());
        } catch (Exception e) {
            log.error("Erro ao enviar email", e);
        }
    }
}
