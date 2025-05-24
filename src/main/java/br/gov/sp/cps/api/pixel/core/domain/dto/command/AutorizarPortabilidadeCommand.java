package br.gov.sp.cps.api.pixel.core.domain.dto.command;

import br.gov.sp.cps.api.pixel.core.domain.dto.PlantacaoDTO;
import br.gov.sp.cps.api.pixel.core.domain.entity.Plantacao;
import lombok.Data;

import java.util.List;

@Data
public class AutorizarPortabilidadeCommand {
    String hashConfirmacao;
    Boolean aceito;

    public AutorizarPortabilidadeCommand(String hashConfirmacao) {
        this.hashConfirmacao = hashConfirmacao;
        this.aceito = true ;
    }
}
