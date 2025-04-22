package br.gov.sp.cps.api.pixel.core.domain.dto;

import java.time.LocalDateTime;
import java.util.List;

public record UsuarioDTO(Long id,
                         String nome,
                         String email,
                         String documento,
                         LocalDateTime dataCriacao,
                         List<PlantacaoDTO> plantacao) {
}
