package br.gov.sp.cps.api.pixel.outbound.jpa;

import br.gov.sp.cps.api.pixel.core.domain.entity.Especie;
import br.gov.sp.cps.api.pixel.core.domain.repository.EspecieRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EspecieJpaRepository extends JpaRepository<Especie, Long>, EspecieRepository {
    default Especie salvar(Especie especie) {
        return save(especie);
    }

    default Optional<Especie> buscarPorId(Long id) {
        return findById(id);
    }
}
