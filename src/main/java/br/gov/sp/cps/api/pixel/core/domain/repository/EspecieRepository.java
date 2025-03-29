package br.gov.sp.cps.api.pixel.core.domain.repository;

import br.gov.sp.cps.api.pixel.core.domain.entity.Especie;
import br.gov.sp.cps.api.pixel.core.domain.entity.Plantacao;

import java.util.Optional;

public interface EspecieRepository {
    Especie salvar(Especie especie);
    Optional<Especie> buscarPorId(Long id);
    Optional<Especie> buscarPorNome(String nome);
}
