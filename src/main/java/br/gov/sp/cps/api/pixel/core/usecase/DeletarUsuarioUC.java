package br.gov.sp.cps.api.pixel.core.usecase;

import br.gov.sp.cps.api.pixel.core.domain.dto.command.DeletarUsuarioCommand;
import br.gov.sp.cps.api.pixel.core.domain.entity.Usuario;
import br.gov.sp.cps.api.pixel.core.domain.repository.ChaveUsuarioRepository;
import br.gov.sp.cps.api.pixel.core.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeletarUsuarioUC {

    private final UsuarioRepository usuarioRepository;
    private final ChaveUsuarioRepository chaveUsuarioRepository;

    public void executar(DeletarUsuarioCommand command){
        Usuario usuario = usuarioRepository.carregar(command.getId())
                .orElseThrow(() -> new RuntimeException("Nenhum usuário encontrado para o ID informado."));
        chaveUsuarioRepository.deletar(usuario.getId());
        usuarioRepository.deletar(usuario);
    }
}
