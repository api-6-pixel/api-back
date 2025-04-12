package br.gov.sp.cps.api.pixel.outbound.mongodb;

import br.gov.sp.cps.api.pixel.core.domain.entity.ChaveUsuario;
import br.gov.sp.cps.api.pixel.core.domain.repository.ChaveUsuarioRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChaveUsuarioMongoRepository extends MongoRepository<ChaveUsuario, String>, ChaveUsuarioRepository {

    @Override
    default void salvar(Long idUsuario, String secretKey) {
        save(new ChaveUsuario(idUsuario,secretKey));
    }
}
