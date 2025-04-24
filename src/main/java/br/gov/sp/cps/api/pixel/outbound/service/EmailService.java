package br.gov.sp.cps.api.pixel.outbound.service;

import br.gov.sp.cps.api.pixel.core.domain.dto.command.EnviarEmailCommand;

public interface EmailService {
    void sendSimpleMail(EnviarEmailCommand command);
}
