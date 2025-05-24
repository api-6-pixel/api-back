package br.gov.sp.cps.api.pixel.core.domain.dto.command;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ObterUsuarioIDCommand {
    private String aesKey;
    private String aesIv;
    private String usuarioID;
    private Long clientID;
    private LocalDateTime tempoExpiracao;

    public ObterUsuarioIDCommand(){}

    public ObterUsuarioIDCommand(String aesKey, String aesIv, String usuarioId, Long clientId) {
        this.aesKey = aesKey;
        this.aesIv = aesIv;
        this.usuarioID = usuarioId;
        this.clientID = clientId;
        this.tempoExpiracao = LocalDateTime.now();
    }
}
