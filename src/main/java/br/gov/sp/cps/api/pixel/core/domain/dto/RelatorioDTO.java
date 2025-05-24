package br.gov.sp.cps.api.pixel.core.domain.dto;

import org.springframework.core.io.InputStreamResource;

public record RelatorioDTO(
        InputStreamResource arquivo,
        String nomeArquivo
) {
}
