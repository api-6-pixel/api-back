package br.gov.sp.cps.api.pixel.core.usecase;

import br.gov.sp.cps.api.pixel.core.domain.dto.UsuarioDTO;
import br.gov.sp.cps.api.pixel.core.domain.entity.ChaveUsuario;
import br.gov.sp.cps.api.pixel.core.domain.entity.Usuario;
import br.gov.sp.cps.api.pixel.core.domain.mapper.UsuarioMapper;
import br.gov.sp.cps.api.pixel.core.domain.repository.ChaveUsuarioRepository;
import br.gov.sp.cps.api.pixel.core.domain.repository.CriptografiaRepository;
import br.gov.sp.cps.api.pixel.core.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CarregarUsuariosUC {

    private final UsuarioRepository usuarioRepository;
    private final CriptografiaRepository criptografiaRepository;
    private final ChaveUsuarioRepository chaveUsuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public List<UsuarioDTO> executarTodos() {
        List<Usuario> usuarios = usuarioRepository.buscarTodos();
        List<UsuarioDTO> usuariosDescriptografados = new ArrayList<>();

        for (Usuario usuario : usuarios) {
            ChaveUsuario chaveUsuario = chaveUsuarioRepository.carregar(usuario.getId());
            if (chaveUsuario != null && chaveUsuario.getChave() != null) {
                try {
                    byte[] decodedKey = Base64.getDecoder().decode(chaveUsuario.getChave());
                    SecretKey secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
                    Usuario descriptografado = (Usuario) criptografiaRepository.getObjectDescriptografado(usuario, secretKey);
                    usuariosDescriptografados.add(usuarioMapper.toDto(descriptografado));
                } catch (Exception e) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao descriptografar dados de um usuário.");
                }
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Chave do usuário não encontrada ou inválida.");
            }
        }

        return usuariosDescriptografados;
    }
}
