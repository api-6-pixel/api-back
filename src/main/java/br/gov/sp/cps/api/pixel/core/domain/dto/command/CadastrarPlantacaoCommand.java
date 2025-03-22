package br.gov.sp.cps.api.pixel.core.domain.dto.command;

import br.gov.sp.cps.api.pixel.core.domain.enumeration.StatusPlantacao;

public record CadastrarPlantacaoCommand(
        String fazendaNome,
        String especieNome,
        Double areaPlantada,
        String tipoSolo,
        Double custoEsperado,
        StatusPlantacao status,
        Long plantacaoId,
        Double temperaturaAmbiente,
        Double temperaturaSolo,
        Double umidadeAmbiente,
        Double umidadeSolo,
        Double phSolo,
        Double indiceUV) {
}
