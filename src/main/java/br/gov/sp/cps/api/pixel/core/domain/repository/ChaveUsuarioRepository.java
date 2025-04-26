package br.gov.sp.cps.api.pixel.core.domain.repository;

import br.gov.sp.cps.api.pixel.core.domain.entity.ChaveUsuario;

public interface ChaveUsuarioRepository {

    void salvar(Long idUsuario, String secretKey);

    void deletar(Long idUsuario);

    ChaveUsuario carregar(Long idUsuario);
}
