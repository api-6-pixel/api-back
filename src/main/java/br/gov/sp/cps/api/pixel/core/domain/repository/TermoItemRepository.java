package br.gov.sp.cps.api.pixel.core.domain.repository;

import br.gov.sp.cps.api.pixel.core.domain.entity.TermoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TermoItemRepository extends JpaRepository<TermoItem, Long> {

    // Retorna todos os itens vinculados a um termo específico
    List<TermoItem> findByTermoCodigo(Long termoCodigo);
}
