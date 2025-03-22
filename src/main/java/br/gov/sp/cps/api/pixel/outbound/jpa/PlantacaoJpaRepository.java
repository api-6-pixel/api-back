package br.gov.sp.cps.api.pixel.outbound.jpa;

import br.gov.sp.cps.api.pixel.core.domain.entity.Plantacao;
import br.gov.sp.cps.api.pixel.core.domain.repository.PlantacaoRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlantacaoJpaRepository extends JpaRepository<Plantacao, Long>, PlantacaoRepository {
    default Plantacao salvar(Plantacao plantacao) {
        return save(plantacao);
    }

    default Optional<Plantacao> buscarPorId(Long id) {
        return findById(id);
    }
}
