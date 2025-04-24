package br.gov.sp.cps.api.pixel.core.usecase;

import br.gov.sp.cps.api.pixel.core.domain.dto.command.AlterarUsuarioCommand;
import br.gov.sp.cps.api.pixel.core.domain.entity.ChaveUsuario;
import br.gov.sp.cps.api.pixel.core.domain.entity.Usuario;
import br.gov.sp.cps.api.pixel.core.domain.repository.ChaveUsuarioRepository;
import br.gov.sp.cps.api.pixel.core.domain.repository.CriptografiaRepository;
import br.gov.sp.cps.api.pixel.core.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class AlterarUsuarioUC {

    private final UsuarioRepository usuarioRepository;
    private final CriptografiaRepository criptografiaRepository;
    private final ChaveUsuarioRepository chaveUsuarioRepository;

    public Usuario executar(AlterarUsuarioCommand command) throws Exception {
        ChaveUsuario chaveUsuario = chaveUsuarioRepository.carregar(command.getId());
        byte[] decodedKey = Base64.getDecoder().decode(chaveUsuario.getChave());
        SecretKey secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");

        command = (AlterarUsuarioCommand) criptografiaRepository.getObjectEncriptografado(command, secretKey);

        Usuario usuarioExistente = usuarioRepository.carregar(command.getId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuarioExistente.setEmail(command.getEmail());
        usuarioExistente.setNome(command.getNome());
        usuarioExistente.setDocumento(command.getDocumento());

        usuarioExistente.atualizarPlantacao(command.getPlantacao());

        //Usuario novoUsuario = Usuario.toEntity(command);
        //novoUsuario.setDataCriacao(usuarioExistente.getDataCriacao());
        //novoUsuario.setSenha(usuarioExistente.getSenha());


        return usuarioRepository.salvar(usuarioExistente);
    }

}
