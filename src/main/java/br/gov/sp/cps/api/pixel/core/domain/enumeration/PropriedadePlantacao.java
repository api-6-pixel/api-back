package br.gov.sp.cps.api.pixel.core.domain.enumeration;

import lombok.Getter;

@Getter
public enum PropriedadePlantacao {

    UMIDADE_SOLO("Umidade solo", 20,40,60,80),
    TEMPERATURA_AMBIENTE("Temperatura ambiente", 10,15,30,35),
    TEMPERATURA_SOLO("Temperatura solo",5,15,30,35),
    UMIDADE_AMBIENTE("Umidade ambiente", 30,50,80,90),
    INDICE_UV("Indice UV", 200,400,1000,1200),
    PH_SOLO("PH Solo", 5.5,6.0,7.5,8.0);

    private String descricao;
    private double ruimMinimo;
    private double aceitavelMinimo;
    private double aceitavelMaximo;
    private double ruimMaximo;

    PropriedadePlantacao(String descricao, double ruimMinimo, double aceitavelMinimo, double aceitavelMaximo, double ruimMaximo) {
        this.descricao = descricao;
        this.ruimMinimo = ruimMinimo;
        this.aceitavelMinimo = aceitavelMinimo;
        this.aceitavelMaximo = aceitavelMaximo;
        this.ruimMaximo = ruimMaximo;
    }
}
