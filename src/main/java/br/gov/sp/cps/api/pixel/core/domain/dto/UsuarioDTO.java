package br.gov.sp.cps.api.pixel.core.domain.dto;

import java.util.List;

public record UsuarioDTO(Long id,
                         String nome,
                         String email,
                         List<PlantacaoDTO> plantacao) {
}
