package br.gov.sp.cps.api.pixel.core.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TermoItemAceiteDTO {
    private Long codigo; 
    private Long usuarioCodigo;
    private Long termoItemCodigo;
    private String descricao;
    private Boolean aceito;

  
    public TermoItemAceiteDTO(Long codigo,String descricao, Boolean aceito) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.aceito = aceito;
    }

    public TermoItemAceiteDTO() {
    }
}
