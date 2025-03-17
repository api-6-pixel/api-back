package br.gov.sp.cps.api.pixel.core.domain.repository;

import br.gov.sp.cps.api.pixel.core.domain.entity.Plantacao;

import java.util.Optional;

public interface PlantacaoRepository {
    Plantacao salvar(Plantacao plantacao);
    Optional<Plantacao> buscarPorId(Long id);
}
