package br.gov.sp.cps.api.pixel.core.domain.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class TermoItemAceiteDetalhadoDTO {
    private Long termoCodigo;
    private Long usuarioCodigo;
    private String usuarioUsername;
    private LocalDateTime dataAceite;
    private Long codigoItem;
    private String termoItem;
    private Boolean aceito;

    public TermoItemAceiteDetalhadoDTO(Long termoCodigo, Long usuarioCodigo, String usuarioUsername,
                                       LocalDateTime dataAceite, Long codigoItem, String termoItem, Boolean aceito) {
        this.termoCodigo = termoCodigo;
        this.usuarioCodigo = usuarioCodigo;
        this.usuarioUsername = usuarioUsername;
        this.dataAceite = dataAceite;
        this.codigoItem = codigoItem;
        this.termoItem = termoItem;
        this.aceito = aceito;
    }
}
