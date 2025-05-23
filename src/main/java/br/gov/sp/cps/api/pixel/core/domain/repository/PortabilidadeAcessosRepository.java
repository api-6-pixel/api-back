package br.gov.sp.cps.api.pixel.core.domain.repository;

import br.gov.sp.cps.api.pixel.core.domain.entity.PortabilidadeAcessos;
import java.util.Optional;

import br.gov.sp.cps.api.pixel.core.domain.entity.ChavePortabilidade;

public interface PortabilidadeAcessosRepository {
    Optional<PortabilidadeAcessos> buscar(String client_id, String client_secret);
}
