package br.gov.sp.cps.api.pixel.outbound.jpa;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.sp.cps.api.pixel.core.domain.entity.ChavePortabilidade;
import br.gov.sp.cps.api.pixel.core.domain.repository.PortabilidadeRepository;

public interface PortabilidadeJpaRepository extends JpaRepository<ChavePortabilidade, String>, PortabilidadeRepository {
    default ChavePortabilidade salvar(ChavePortabilidade especie) {
        return save(especie);
    }
}
