package br.gov.sp.cps.api.pixel.outbound.jpa;

import br.gov.sp.cps.api.pixel.core.domain.entity.Fazenda;
import br.gov.sp.cps.api.pixel.core.domain.repository.FazendaRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FazendaJpaRepository extends JpaRepository<Fazenda, Long>, FazendaRepository {
    default Fazenda salvar(Fazenda fazenda){
        return save(fazenda);
    }

    default Optional<Fazenda> buscarPorId(Long id){
        return findById(id);
    }
}
