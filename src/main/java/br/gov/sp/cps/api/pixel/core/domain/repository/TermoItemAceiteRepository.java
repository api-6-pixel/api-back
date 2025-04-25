package br.gov.sp.cps.api.pixel.core.domain.repository;

import br.gov.sp.cps.api.pixel.core.domain.dto.TermoItemDTO;
import br.gov.sp.cps.api.pixel.core.domain.entity.TermoItemAceite;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TermoItemAceiteRepository extends JpaRepository<TermoItemAceite, Long> {
    Optional<TermoItemAceite> findByTermoAceiteUsuarioHistoricoUsuarioIdAndTermoItemCodigo(Long usuarioId,
            Long termoItemCodigo);

    Optional<TermoItemAceite> findTopByTermoAceiteUsuarioHistoricoUsuarioIdAndTermoItemCodigoOrderByCodigoDesc(
            Long usuarioCodigo, Long termoItemCodigo);

  
        }
