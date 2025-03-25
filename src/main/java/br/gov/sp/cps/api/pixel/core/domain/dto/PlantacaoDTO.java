package br.gov.sp.cps.api.pixel.core.domain.dto;

import br.gov.sp.cps.api.pixel.core.domain.enumeration.StatusPlantacao;

import java.time.LocalDateTime;

public record PlantacaoDTO(
        Long id,
        String fazendaNome,
        String especieNome,
        Double areaPlantada,
        LocalDateTime dataPlantio,
        Double custoEsperado,
        StatusPlantacao status) {
}
