package br.gov.sp.cps.api.pixel.core.domain.dto;

import br.gov.sp.cps.api.pixel.core.domain.enumeration.StatusPlantacao;

import java.time.LocalDateTime;

public record DadosRelatorioDTO(
        String fazendaNome,
        String especieNome,
        Double areaPlantada,
        Double custoEsperado,
        StatusPlantacao status,
        LocalDateTime dataPlantio,
        Double temperaturaAmbiente,
        Double temperaturaSolo,
        Double umidadeAmbiente,
        Double umidadeSolo,
        Double phSolo,
        Double indiceUV,
        LocalDateTime dataAtualizacao
) {
}
