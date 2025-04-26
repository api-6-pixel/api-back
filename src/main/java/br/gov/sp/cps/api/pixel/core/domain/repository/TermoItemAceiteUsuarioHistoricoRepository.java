package br.gov.sp.cps.api.pixel.core.domain.repository;

import br.gov.sp.cps.api.pixel.core.domain.entity.TermoItemAceiteUsuarioHistorico;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TermoItemAceiteUsuarioHistoricoRepository extends JpaRepository<TermoItemAceiteUsuarioHistorico, Long> {

    Optional<TermoItemAceiteUsuarioHistorico> findTopByUsuario_IdOrderByDataAceiteDesc(Long usuarioId);
}
