package br.gov.sp.cps.api.pixel.core.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TermoComItensDTO {

    private Long termoId;
    private String descricao;
    
    private boolean aceito;  

    private List<ItemDTO> obrigatorios;
    private List<ItemDTO> opcionais;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemDTO {
        private Long codigo;
        private String descricao;
        private boolean aceito;
    }
}
