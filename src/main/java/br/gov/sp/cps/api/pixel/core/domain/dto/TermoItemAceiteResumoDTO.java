package br.gov.sp.cps.api.pixel.core.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TermoItemAceiteResumoDTO {

    private Long termoItemCodigo;
    private String descricao;
    private Boolean aceito;
    private Long historicoCodigo; // h.codigo

    public TermoItemAceiteResumoDTO(Long termoItemCodigo, String descricao, Boolean aceito, Long historicoCodigo) {
        this.termoItemCodigo = termoItemCodigo;
        this.descricao = descricao;
        this.aceito = aceito;
        this.historicoCodigo = historicoCodigo;
    }

    public TermoItemAceiteResumoDTO() {}
}
