package br.gov.sp.cps.api.pixel.core.usecase;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import org.springframework.stereotype.Service;

import br.gov.sp.cps.api.pixel.core.domain.dto.CriarChavePortabilidadeDTO;
import br.gov.sp.cps.api.pixel.core.domain.dto.command.PortabilidadeCriarChaveCommand;
import br.gov.sp.cps.api.pixel.core.domain.entity.ChavePortabilidade;
import br.gov.sp.cps.api.pixel.core.domain.repository.PortabilidadeRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CriarChavePortabilidadeUC {
    private final PortabilidadeRepository portabilidadeRepository;
    
    public CriarChavePortabilidadeDTO executar(PortabilidadeCriarChaveCommand command) throws Exception{
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048); 
        KeyPair keyPair = generator.generateKeyPair();
        
        ChavePortabilidade novaChave = new ChavePortabilidade();
        novaChave.setTempoExp(command.getTempoExpiracao());
        novaChave.setLibChavePublica(command.getChave()); 
        novaChave.setMinhaChavePrivada(keyPair.getPrivate().toString());
        portabilidadeRepository.salvar(novaChave);
        
        return new CriarChavePortabilidadeDTO(keyPair.getPublic().toString(), novaChave.getId().toString());    
    }
}
