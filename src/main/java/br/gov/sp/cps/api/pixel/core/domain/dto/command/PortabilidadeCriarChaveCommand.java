package br.gov.sp.cps.api.pixel.core.domain.dto.command;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class PortabilidadeCriarChaveCommand {
    private String chave;
    private LocalDateTime tempoExpiracao; 
}
