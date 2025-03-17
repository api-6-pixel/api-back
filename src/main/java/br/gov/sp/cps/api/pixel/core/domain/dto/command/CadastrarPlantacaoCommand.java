package br.gov.sp.cps.api.pixel.core.domain.dto.command;

import br.gov.sp.cps.api.pixel.core.domain.enumeration.StatusPlantacao;

public record CadastrarPlantacaoCommand(
        Long fazendaId,
        Long especieId,
        Double areaPlantada,
        String tipoSolo,
        StatusPlantacao status) {
}
