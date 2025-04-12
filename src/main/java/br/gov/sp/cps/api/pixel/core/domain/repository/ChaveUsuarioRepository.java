package br.gov.sp.cps.api.pixel.core.domain.repository;

public interface ChaveUsuarioRepository {

    void salvar(Long idUsuario, String secretKey);
}
