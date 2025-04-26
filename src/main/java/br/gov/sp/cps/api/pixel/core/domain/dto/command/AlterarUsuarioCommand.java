package br.gov.sp.cps.api.pixel.core.domain.dto.command;

import br.gov.sp.cps.api.pixel.core.domain.dto.PlantacaoDTO;
import br.gov.sp.cps.api.pixel.core.domain.entity.Plantacao;
import lombok.Data;

import java.util.List;

@Data
public class AlterarUsuarioCommand {
    private Long id;
    private String nome;
    private String email;
    private String documento;
    private List<PlantacaoDTO>plantacao;
}
