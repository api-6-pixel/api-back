package br.gov.sp.cps.api.pixel.core.service;

import br.gov.sp.cps.api.pixel.core.domain.enumeration.CondicaoPlantacao;

public class ClassificadorPlantacaoService {

    public CondicaoPlantacao classificarFaixa(double valor, double ruimMin, double aceitMin, double aceitMax, double ruimMax) {
        if (valor < ruimMin || valor > ruimMax) {
            return CondicaoPlantacao.RUIM;
        } else if ((valor >= ruimMin && valor < aceitMin) || (valor > aceitMax && valor <= ruimMax)) {
            return CondicaoPlantacao.ACEITAVEL;
        } else {
            return CondicaoPlantacao.IDEAL;
        }
    }
}
