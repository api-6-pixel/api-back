package br.gov.sp.cps.api.pixel.core.domain.dto.command;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class PortabilidadeLoginCommand{
    private String clientId;
    private String clientSecret;
}
