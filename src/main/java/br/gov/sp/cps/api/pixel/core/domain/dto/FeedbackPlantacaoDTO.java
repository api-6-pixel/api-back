package br.gov.sp.cps.api.pixel.core.domain.dto;

import lombok.Data;

@Data
public class FeedbackPlantacaoDTO {

    private String propriedade;
    private boolean melhorar;
    private double valorAtual;
    private String status;
}
