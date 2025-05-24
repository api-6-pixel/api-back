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
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class CarregarUsuarioIdUC {

    private final UsuarioRepository usuarioRepository;
    private final CriptografiaRepository criptografiaRepository;
    private final ChaveUsuarioRepository chaveUsuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public UsuarioDTO executar(Long idUsuario) {
        Usuario usuario = usuarioRepository.carregar(idUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário não encontrado para o ID informado."));

        ChaveUsuario chaveUsuario = chaveUsuarioRepository.carregar(idUsuario);
        if (chaveUsuario == null || chaveUsuario.getChave() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Chave do usuário não encontrada ou inválida.");
        }

        try {
            byte[] decodedKey = Base64.getDecoder().decode(chaveUsuario.getChave());
            SecretKey secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
            Usuario descriptografado = (Usuario) criptografiaRepository.getObjectDescriptografado(usuario, secretKey);
            return usuarioMapper.toDto(descriptografado);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao descriptografar os dados do usuário.");
        }
    }


}
