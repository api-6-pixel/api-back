package br.gov.sp.cps.api.pixel.core.usecase;

import br.gov.sp.cps.api.pixel.core.domain.dto.command.AlterarUsuarioCommand;
import br.gov.sp.cps.api.pixel.core.domain.dto.command.AutorizarPortabilidadeCommand;
import br.gov.sp.cps.api.pixel.core.domain.entity.ChaveUsuario;
import br.gov.sp.cps.api.pixel.core.domain.entity.Usuario;
import br.gov.sp.cps.api.pixel.core.domain.repository.ChaveUsuarioRepository;
import br.gov.sp.cps.api.pixel.core.domain.repository.CriptografiaRepository;
import br.gov.sp.cps.api.pixel.core.domain.repository.UsuarioRepository;
import br.gov.sp.cps.api.pixel.core.domain.entity.ChavePortabilidade;
import br.gov.sp.cps.api.pixel.core.domain.repository.PortabilidadeRepository;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Service
@Transactional
@RequiredArgsConstructor
public class AutorizarPortabilidadeUC {

    private final PortabilidadeRepository portabilidadeRepository;
    
    public void executar(AutorizarPortabilidadeCommand command) throws Exception {
        var chave = portabilidadeRepository.buscarPorHash(URLDecoder.decode(command.getHashConfirmacao(), StandardCharsets.UTF_8));
        chave.setAutenticado(command.getAceito());
    }
}
