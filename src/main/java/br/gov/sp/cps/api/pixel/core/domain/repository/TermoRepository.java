package br.gov.sp.cps.api.pixel.core.domain.repository;

import br.gov.sp.cps.api.pixel.core.domain.entity.Termo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TermoRepository extends JpaRepository<Termo, Long> {
    Termo findTopByOrderByCodigoDesc(); 
}
