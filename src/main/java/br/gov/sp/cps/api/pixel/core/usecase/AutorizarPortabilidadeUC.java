package br.gov.sp.cps.api.pixel.core.usecase;

import br.gov.sp.cps.api.pixel.core.domain.dto.PortabilidadeDTO;
import br.gov.sp.cps.api.pixel.core.domain.dto.UsuarioDTO;
import br.gov.sp.cps.api.pixel.core.domain.dto.command.AutorizarPortabilidadeCommand;
import br.gov.sp.cps.api.pixel.core.domain.dto.command.ObterUsuarioIDCommand;
import br.gov.sp.cps.api.pixel.core.domain.entity.ChavePortabilidade;
import br.gov.sp.cps.api.pixel.core.domain.repository.PortabilidadeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.gov.sp.cps.api.pixel.core.domain.dto.command.AlterarUsuarioCommand;
import java.net.URI;
import org.springframework.web.client.RestTemplate;

@Service
@Transactional
@RequiredArgsConstructor
public class AutorizarPortabilidadeUC {
    private final PortabilidadeRepository portabilidadeRepository;
    private final CarregarUsuarioIdUC usuarioUc;
 
    public void executar(AutorizarPortabilidadeCommand command) throws Exception {
        var chave = portabilidadeRepository.buscarPorHash(URLEncoder.encode(command.getHashConfirmacao()));
        chave.setAutenticado(command.getAceito());

        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(256);
        SecretKey secretKey = generator.generateKey();

        byte[] iv = new byte[16];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(iv);

        var response = getUserToSend(chave, iv, secretKey.getEncoded(), chave.getUsuario().getId());

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForEntity("http://localhost:5000/webhook-user", response, String.class);
    }

    private PortabilidadeDTO getUserToSend(ChavePortabilidade chave, byte[] iv, byte[] key, Long usuarioId) throws Exception    {
        UsuarioDTO usuario = usuarioUc.executar(usuarioId);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String usuarioJson = objectMapper.writeValueAsString(usuario);

        Cipher cipherEnc = ObterUsuarioUC.createAESCipher(key, iv, Cipher.ENCRYPT_MODE);
        byte[] encryptedBytes = cipherEnc.doFinal(usuarioJson.getBytes(StandardCharsets.UTF_8));
        String dados = Base64.getEncoder().encodeToString(encryptedBytes);
        
        PublicKey chavePublica = ObterUsuarioUC.getChavePublicaDeTexto(chave.getLibChavePublica());
        byte[] novaChave = ObterUsuarioUC.criptografar(Base64.getEncoder().encodeToString(key), chavePublica);
        byte[] novaIv = ObterUsuarioUC.criptografar(Base64.getEncoder().encodeToString(iv), chavePublica);

        return new PortabilidadeDTO(dados, Base64.getEncoder().encodeToString(novaChave),Base64.getEncoder().encodeToString(novaIv));

    }
}
