package br.gov.sp.cps.api.pixel.core.domain.dto;

public record LoginDTO(
        String token, Long idUsuario, String funcao
) {
}
