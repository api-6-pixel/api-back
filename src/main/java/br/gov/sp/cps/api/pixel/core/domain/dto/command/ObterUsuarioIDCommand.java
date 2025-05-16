package br.gov.sp.cps.api.pixel.core.domain.dto.command;

import lombok.Data;

@Data
public class ObterUsuarioIDCommand {
    private String usuarioID;
    private String aesKey;
    private String aesIv;
    private Long clientID;
}
