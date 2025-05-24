package br.gov.sp.cps.api.pixel.core.domain.repository;

import br.gov.sp.cps.api.pixel.core.domain.entity.Usuario;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository {
    Usuario salvar(Usuario usuario);

    Optional<Usuario> carregar(Long id);

    void deletar(Usuario usuario);

    Usuario buscarPorNomeUsuario(String nomeUsuario);

    Usuario buscarPorEmail(String nomeUsuario);

    List<Usuario> buscarTodos();

}
