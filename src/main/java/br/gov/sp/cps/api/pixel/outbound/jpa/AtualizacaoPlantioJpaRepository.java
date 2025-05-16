package br.gov.sp.cps.api.pixel.outbound.jpa;

import br.gov.sp.cps.api.pixel.core.domain.entity.AtualizacaoPlantio;
import br.gov.sp.cps.api.pixel.core.domain.entity.Plantacao;
import br.gov.sp.cps.api.pixel.core.domain.repository.AtualizacaoPlantioRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AtualizacaoPlantioJpaRepository extends
        JpaRepository<AtualizacaoPlantio, Long>,
        AtualizacaoPlantioRepository {
    default AtualizacaoPlantio salvar(AtualizacaoPlantio atualizacaoPlantio) {
        return save(atualizacaoPlantio);
    }

    default List<AtualizacaoPlantio> buscarPorPlantacao(Plantacao plantacao) {
        return findByPlantacao(plantacao);
    }

    List<AtualizacaoPlantio> findByPlantacao(Plantacao plantacao);
}
