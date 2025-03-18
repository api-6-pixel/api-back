package br.gov.sp.cps.api.pixel.core.domain.dto;

import java.time.LocalDateTime;

public record AtualizacaoPlantioDTO(
        Long id,
        Long plantacaoId,
        Double temperaturaAmbiente,
        Double temperaturaSolo,
        Double umidadeAmbiente,
        Double umidadeSolo,
        Double phSolo,
        Double precipitacao,
        Double indiceUV,
        LocalDateTime dataRegistro) {
}
