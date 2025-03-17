package br.gov.sp.cps.api.pixel.core.domain.repository;

import br.gov.sp.cps.api.pixel.core.domain.entity.Fazenda;

import java.util.Optional;

public interface FazendaRepository {
    Fazenda salvar(Fazenda fazenda);
    Optional<Fazenda> buscarPorId(Long id);
}
