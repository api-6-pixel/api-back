package br.gov.sp.cps.api.pixel.core.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TermoItemAceiteDTO {
    private Long usuarioCodigo;
    private Long termoItemCodigo;
    private String descricao; // Descrição do termo item
    private boolean aceito;
}
