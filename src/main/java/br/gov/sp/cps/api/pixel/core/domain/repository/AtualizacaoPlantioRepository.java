package br.gov.sp.cps.api.pixel.core.domain.repository;

import br.gov.sp.cps.api.pixel.core.domain.entity.AtualizacaoPlantio;
import br.gov.sp.cps.api.pixel.core.domain.entity.Plantacao;

import java.util.List;
import java.util.stream.Stream;

public interface AtualizacaoPlantioRepository {
    AtualizacaoPlantio salvar(AtualizacaoPlantio atualizacaoPlantio);
    List<AtualizacaoPlantio> buscarPorPlantacao(Plantacao plantacao);
}
