package br.gov.sp.cps.api.pixel.core.domain.dto.command;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ObterUsuarioIDCommand {
    private String aesKey;
    private String aesIv;
    private String usuarioID;
    private Long clientID;
    private LocalDateTime tempoExpiracao;
}
