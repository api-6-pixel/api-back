package br.gov.sp.cps.api.pixel.core.domain.mapper;

import br.gov.sp.cps.api.pixel.core.domain.dto.PlantacaoDTO;
import br.gov.sp.cps.api.pixel.core.domain.dto.UsuarioDTO;
import br.gov.sp.cps.api.pixel.core.domain.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsuarioMapper {

    public UsuarioDTO toDto(Usuario usuario) {
        List<PlantacaoDTO> plantacao = usuario.getPlantacao().stream()
                .map(f -> new PlantacaoDTO(f.getId(),f.getFazendaNome(),f.getEspecieNome(),f.getAreaPlantada(),f.getDataPlantio(),f.getCustoEsperado(),f.getStatus()))
                .toList();
        return new UsuarioDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                plantacao
        );
    }
}
