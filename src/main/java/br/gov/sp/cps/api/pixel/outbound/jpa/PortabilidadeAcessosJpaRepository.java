package br.gov.sp.cps.api.pixel.outbound.jpa;
import br.gov.sp.cps.api.pixel.core.domain.entity.PortabilidadeAcessos;
import br.gov.sp.cps.api.pixel.core.domain.repository.PortabilidadeRepository;
import br.gov.sp.cps.api.pixel.core.domain.repository.PortabilidadeAcessosRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PortabilidadeAcessosJpaRepository extends JpaRepository<PortabilidadeAcessos, String>, PortabilidadeAcessosRepository {
    default Optional<PortabilidadeAcessos> buscar(String clientId, String clientSecret){
        return findByClientIdAndClientSecret(clientId, clientSecret);
    }

    Optional<PortabilidadeAcessos> findByClientIdAndClientSecret(String clientId, String clientSecret);
}
