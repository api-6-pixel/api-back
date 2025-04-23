package br.gov.sp.cps.api.pixel.core.domain.dto.command;

public record EnviarEmailCommand(
        String recipient,
        String msgBody,
        String subject,
        String attachment
) {
}
