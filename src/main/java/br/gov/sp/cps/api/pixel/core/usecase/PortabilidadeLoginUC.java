package br.gov.sp.cps.api.pixel.core.usecase;
import br.gov.sp.cps.api.pixel.core.domain.dto.PortabilidadeLoginDTO;
import br.gov.sp.cps.api.pixel.core.domain.dto.command.PortabilidadeLoginCommand;
import br.gov.sp.cps.api.pixel.core.domain.entity.Usuario;
import br.gov.sp.cps.api.pixel.core.domain.dto.PortabilidadeLoginDTO;
import br.gov.sp.cps.api.pixel.core.domain.dto.PortabilidadeLoginDTO;
import br.gov.sp.cps.api.pixel.core.domain.repository.PortabilidadeAcessosRepository;
import br.gov.sp.cps.api.pixel.core.domain.repository.UsuarioRepository;
import br.gov.sp.cps.api.pixel.core.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.gov.sp.cps.api.pixel.core.domain.dto.LoginDTO;
import br.gov.sp.cps.api.pixel.core.domain.repository.UsuarioRepository;
 


@Service
@RequiredArgsConstructor
@Transactional
public class PortabilidadeLoginUC{
    private final TokenService tokenService;
    private final PortabilidadeAcessosRepository portabilidadeRepository;

    public PortabilidadeLoginDTO executar(PortabilidadeLoginCommand command) {
        System.out.println(command.getClientId());
        System.out.println(command.getClientSecret());
        var acesso = portabilidadeRepository.buscar(command.getClientId(),command.getClientSecret())
            .orElseThrow(() -> new RuntimeException("Client id/ Client secret invalido."));
        var token = tokenService.gerarToken(acesso);
        return new PortabilidadeLoginDTO(token);
    }
} 

