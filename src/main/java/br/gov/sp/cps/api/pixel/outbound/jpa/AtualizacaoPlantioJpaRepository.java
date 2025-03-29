package br.gov.sp.cps.api.pixel.outbound.jpa;

import br.gov.sp.cps.api.pixel.core.domain.entity.AtualizacaoPlantio;
import br.gov.sp.cps.api.pixel.core.domain.repository.AtualizacaoPlantioRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AtualizacaoPlantioJpaRepository extends
        JpaRepository<AtualizacaoPlantio, Long>,
        AtualizacaoPlantioRepository {
    default AtualizacaoPlantio salvar(AtualizacaoPlantio atualizacaoPlantio) {
        return save(atualizacaoPlantio);
    }
}
