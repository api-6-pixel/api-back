package br.gov.sp.cps.api.pixel.core.domain.dto;

import java.time.LocalDateTime;

public record FazendaDTO(
        Long id,
        String nome,
        String localizacao,
        Double areaTotal,
        LocalDateTime dataCriacao) {
}
