package br.gov.sp.cps.api.pixel.outbound.jpa;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.sp.cps.api.pixel.core.domain.entity.ChavePortabilidade;
import br.gov.sp.cps.api.pixel.core.domain.repository.PortabilidadeRepository;

public interface PortabilidadeJpaRepository extends JpaRepository<ChavePortabilidade, Long>, PortabilidadeRepository {
    default ChavePortabilidade salvar(ChavePortabilidade especie) {
        return save(especie);
    }

  default Optional<ChavePortabilidade> buscarPorId(Long id) {
        return findById(id);
    }

  default ChavePortabilidade buscarPorHash(String hash) {
        return findByHashConfirmacao(hash);
    }


    ChavePortabilidade findByHashConfirmacao(String hash);
}
