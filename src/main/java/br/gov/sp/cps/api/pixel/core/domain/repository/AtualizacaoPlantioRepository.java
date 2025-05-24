package br.gov.sp.cps.api.pixel.core.domain.repository;

import br.gov.sp.cps.api.pixel.core.domain.entity.AtualizacaoPlantio;
import br.gov.sp.cps.api.pixel.core.domain.entity.Plantacao;

import java.util.List;
import java.util.stream.Stream;

import java.util.List;
import java.util.Optional;

public interface AtualizacaoPlantioRepository {
    AtualizacaoPlantio salvar(AtualizacaoPlantio atualizacaoPlantio);
    Optional<AtualizacaoPlantio> carregar(Long id);
    List<AtualizacaoPlantio> carregar();
    List<AtualizacaoPlantio> buscarPorPlantacao(Plantacao plantacao);
}
