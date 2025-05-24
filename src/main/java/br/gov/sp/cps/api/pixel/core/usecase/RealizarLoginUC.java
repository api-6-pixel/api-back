package br.gov.sp.cps.api.pixel.core.usecase;

import br.gov.sp.cps.api.pixel.core.domain.dto.AutenticacaoDTO;
import br.gov.sp.cps.api.pixel.core.domain.dto.LoginDTO;
import br.gov.sp.cps.api.pixel.core.domain.entity.ChaveUsuario;
import br.gov.sp.cps.api.pixel.core.domain.entity.Usuario;
import br.gov.sp.cps.api.pixel.core.domain.repository.ChaveUsuarioRepository;
import br.gov.sp.cps.api.pixel.core.domain.repository.CriptografiaRepository;
import br.gov.sp.cps.api.pixel.core.domain.repository.UsuarioRepository;
import br.gov.sp.cps.api.pixel.core.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class RealizarLoginUC {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UsuarioRepository usuarioRepository;
    private final CriptografiaRepository criptografiaRepository;
    private final ChaveUsuarioRepository chaveUsuarioRepository;

    public LoginDTO realizarLogin(AutenticacaoDTO data) {
        Usuario usuario = usuarioRepository.buscarPorNomeUsuario(data.nomeUsuario());
        ChaveUsuario chaveUsuario = chaveUsuarioRepository.carregar(usuario.getId());

        try {
            // Descriptografa os dados do usuário
            byte[] decodedKey = Base64.getDecoder().decode(chaveUsuario.getChave());
            SecretKey secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
            Usuario usuarioDescriptografado = (Usuario) criptografiaRepository.getObjectDescriptografado(usuario, secretKey);


            var usernamePassword = new UsernamePasswordAuthenticationToken(data.nomeUsuario(), data.senha());
            var auth = authenticationManager.authenticate(usernamePassword);
            var token = tokenService.gerarToken((Usuario) auth.getPrincipal());

            return new LoginDTO(token,usuario.getId(),usuarioDescriptografado.getFuncao());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}