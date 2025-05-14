package br.gov.sp.cps.api.pixel.core.domain.dto.command;

import lombok.Data;

@Data
public class ObterUsuarioIDCommand {
    private String usuarioID;
    private Long clientID;
}
