package br.gov.sp.cps.api.pixel.core.domain.dto;

import br.gov.sp.cps.api.pixel.core.domain.enumeration.StatusPlantacao;

import java.time.LocalDateTime;

public record PlantacaoDTO(
        Long id,
        Long fazendaId,
        Long especieId,
        Double areaPlantada,
        String tipoSolo,
        LocalDateTime dataPlantio,
        StatusPlantacao status) {
}
