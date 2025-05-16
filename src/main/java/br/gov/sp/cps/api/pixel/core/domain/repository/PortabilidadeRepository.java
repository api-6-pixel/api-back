package br.gov.sp.cps.api.pixel.core.domain.repository;

import java.util.Optional;

import br.gov.sp.cps.api.pixel.core.domain.entity.ChavePortabilidade;

public interface PortabilidadeRepository {
    ChavePortabilidade salvar(ChavePortabilidade chave);
    Optional<ChavePortabilidade> buscarPorId(Long id);
}
