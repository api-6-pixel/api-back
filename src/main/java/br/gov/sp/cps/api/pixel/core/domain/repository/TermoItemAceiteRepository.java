package br.gov.sp.cps.api.pixel.core.domain.repository;

import br.gov.sp.cps.api.pixel.core.domain.dto.TermoItemAceiteDTO;
import br.gov.sp.cps.api.pixel.core.domain.dto.TermoItemAceiteDetalhadoDTO;
import br.gov.sp.cps.api.pixel.core.domain.entity.TermoItemAceite;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TermoItemAceiteRepository extends JpaRepository<TermoItemAceite, Long> {

    @Modifying
    @Transactional
    @Query("""
                UPDATE TermoItemAceite a
                SET a.aceito = :novoValor
                WHERE a IN (
                    SELECT a2 FROM TermoItemAceite a2
                    JOIN a2.termoAceiteUsuarioHistorico h
                    WHERE h.usuario.id = :usuarioId AND
                    a2.codigo = :termoCodigo

                )
            """)
    int atualizarAceitoPorUsuario(@Param("usuarioId") Long usuarioId, @Param("termoCodigo") Long termoCodigo,
            @Param("novoValor") Boolean novoValor);

    @Modifying

    @Transactional
    @Query("""
                SELECT new br.gov.sp.cps.api.pixel.core.domain.dto.TermoItemAceiteDetalhadoDTO(
                    h.termo.codigo,
                    u.id,
                    u.nomeUsuario,
                    h.dataAceite,
                    ti.codigo,
                    ti.descricao,
                    a.aceito
                )
                FROM TermoItemAceiteUsuarioHistorico h
                JOIN h.usuario u
                JOIN TermoItemAceite a ON a.termoAceiteUsuarioHistorico.codigo = h.codigo
                JOIN a.termoItem ti
                WHERE u.id = :usuarioId
                ORDER BY h.dataAceite DESC
            """)
    List<TermoItemAceiteDetalhadoDTO> buscarDetalhesPorUsuario(@Param("usuarioId") Long usuarioId);

    @Query("""
                SELECT new br.gov.sp.cps.api.pixel.core.domain.dto.TermoItemAceiteDTO(
                    ti.codigo,

                    ti.descricao,
                    a.aceito
                )
                FROM TermoItemAceite a
                JOIN a.termoAceiteUsuarioHistorico h
                JOIN a.termoItem ti
                WHERE h.usuario.id = :usuarioId
            """)
    List<TermoItemAceiteDTO> findAceitesByUsuarioId(@Param("usuarioId") Long usuarioId);

    List<TermoItemAceite> findAllByTermoAceiteUsuarioHistoricoUsuarioIdAndAceitoTrue(Long usuarioId);

    Optional<TermoItemAceite> findByTermoAceiteUsuarioHistoricoCodigoAndTermoItemCodigo(Long usuarioId,
            Long itemId);

    Optional<TermoItemAceite> findByTermoAceiteUsuarioHistoricoUsuarioIdAndTermoItemCodigo(Long usuarioId,
            Long termoItemCodigo);

    Optional<TermoItemAceite> findTopByTermoAceiteUsuarioHistoricoUsuarioIdAndTermoItemCodigoOrderByCodigoDesc(
            Long usuarioId, Long termoItemCodigo);

}
