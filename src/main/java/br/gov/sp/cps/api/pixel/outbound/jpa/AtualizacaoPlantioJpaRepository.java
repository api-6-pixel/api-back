package br.gov.sp.cps.api.pixel.outbound.jpa;

import br.gov.sp.cps.api.pixel.core.domain.entity.AtualizacaoPlantio;
import br.gov.sp.cps.api.pixel.core.domain.repository.AtualizacaoPlantioRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AtualizacaoPlantioJpaRepository extends
        JpaRepository<AtualizacaoPlantio, Long>,
        AtualizacaoPlantioRepository {
    default AtualizacaoPlantio salvar(AtualizacaoPlantio atualizacaoPlantio) {
        return save(atualizacaoPlantio);
    }

    default Optional<AtualizacaoPlantio> carregar(Long id){
        return findById(id);
    }

    default List<AtualizacaoPlantio> carregar(){
        return findAll();
    }
}
