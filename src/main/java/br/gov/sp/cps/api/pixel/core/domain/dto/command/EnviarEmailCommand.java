package br.gov.sp.cps.api.pixel.core.domain.dto.command;

import java.util.List;

public record EnviarEmailCommand(
        List<String> recipients,
        String msgBody,
        String subject,
        String attachment
) {
}
