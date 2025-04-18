package br.gov.sp.cps.api.pixel.core.usecase;

import br.gov.sp.cps.api.pixel.core.domain.dto.command.CadastrarUsuarioCommand;
import br.gov.sp.cps.api.pixel.core.domain.entity.Usuario;
import br.gov.sp.cps.api.pixel.core.domain.repository.ChaveUsuarioRepository;
import br.gov.sp.cps.api.pixel.core.domain.repository.CriptografiaRepository;
import br.gov.sp.cps.api.pixel.core.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class CadastrarUsuarioUC {

    private final UsuarioRepository usuarioRepository;
    private final CriptografiaRepository criptografiaRepository;
    private final ChaveUsuarioRepository chaveUsuarioRepository;

    public Usuario executar(CadastrarUsuarioCommand command) throws Exception {
        if (usuarioRepository.buscarPorEmail(command.getEmail()) != null) {
            throw new IllegalArgumentException("Email já cadastrado");
        }

        String senhaCriptografada = new BCryptPasswordEncoder().encode(command.getSenha());
        command.setSenha(senhaCriptografada);

        SecretKey secretKey = criptografiaRepository.gerarChave();
        String key = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        command = (CadastrarUsuarioCommand) criptografiaRepository.getObjectEncriptografado(command, secretKey);
        Usuario usuario = usuarioRepository.salvar(Usuario.toEntity(command));
        chaveUsuarioRepository.salvar(usuario.getId(), key);
        return usuario;
    }
}
