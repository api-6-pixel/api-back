package br.gov.sp.cps.api.pixel.core.usecase;

import br.gov.sp.cps.api.pixel.core.domain.repository.UsuarioRepository;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;
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
        generator.initialize(4096); 
        KeyPair keyPair = generator.generateKeyPair();
        
        ChavePortabilidade novaChave = new ChavePortabilidade();
        novaChave.setAutenticado(false);
        novaChave.setLibChavePublica(Base64.getEncoder().encodeToString(command.getChave().getBytes())); 
        novaChave.setMinhaChavePrivada(Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded()));
        portabilidadeRepository.salvar(novaChave);
        
        return new CriarChavePortabilidadeDTO(toXml((RSAPublicKey)keyPair.getPublic()), novaChave.getId().toString());    
    }

     public static String toXml(RSAPublicKey publicKey) {
        String modulus = Base64.getEncoder().encodeToString(publicKey.getModulus().toByteArray());
        String exponent = Base64.getEncoder().encodeToString(publicKey.getPublicExponent().toByteArray());

        return "<RSAKeyValue>" +
               "<Modulus>" + modulus + "</Modulus>" +
               "<Exponent>" + exponent + "</Exponent>" +
               "</RSAKeyValue>";
    }
}
