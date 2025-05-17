package br.gov.sp.cps.api.pixel.core.domain.dto;

import br.gov.sp.cps.api.pixel.core.domain.enumeration.CondicaoPlantacao;
import lombok.Data;

import java.time.LocalDate;

@Data
public class StatusDTO {

    private CondicaoPlantacao status;
    private LocalDate data;
}
