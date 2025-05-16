package br.gov.sp.cps.api.pixel.core.usecase;
import java.time.LocalDateTime;
import java.util.Base64;

import javax.crypto.Cipher;

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.gov.sp.cps.api.pixel.core.domain.dto.PortabilidadeDTO;
import br.gov.sp.cps.api.pixel.core.domain.dto.UsuarioDTO;
import br.gov.sp.cps.api.pixel.core.domain.dto.command.ObterUsuarioIDCommand;
import br.gov.sp.cps.api.pixel.core.domain.entity.ChavePortabilidade;
import br.gov.sp.cps.api.pixel.core.domain.repository.PortabilidadeRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class ObterUsuarioUC {
    
    private final CarregarUsuarioUC usuarioUc;
    private final PortabilidadeRepository portabilidadeRepository;

    public PortabilidadeDTO executar (ObterUsuarioIDCommand command) throws Exception{
        ChavePortabilidade chave = portabilidadeRepository.findById(command.getClientID())
        .orElseThrow(() -> new RuntimeException("Nenhuma chave encontrada para o ID informado."));
        
         LocalDateTime agora = LocalDateTime.now();

        if (agora.isAfter(chave.getTempoExp())) {
            throw new RuntimeException("Chave expirada");
        } else {
            ObjectMapper mapper = new ObjectMapper();
                
            String textoDescriptografado = 
                descriptografar(command.getUsuarioID().getBytes(), getChavePrivadaDeTexto(chave.getMinhaChavePrivada()));
            
            UsuarioDTO usuario = usuarioUc.executar(Long.parseLong(textoDescriptografado));
            String usuarioJson = mapper.writeValueAsString(usuario);

            byte[] dadosCriptografados = criptografar(usuarioJson, getChavePublicaDeTexto(chave.getLibChavePublica()));
            return new PortabilidadeDTO(Base64.getEncoder().encodeToString(dadosCriptografados));
        }
    } 

    public static byte[] criptografar(String texto, PublicKey chavePublica) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, chavePublica);
        return cipher.doFinal(texto.getBytes());
    }

    public static String descriptografar(byte[] textoCriptografado, PrivateKey chavePrivada) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, chavePrivada);
        byte[] bytesDescriptografados = cipher.doFinal();
        return new String(bytesDescriptografados);
    }

    public static PublicKey getChavePublicaDeTexto(String base64Chave) throws Exception {
        byte[] bytesChave = Base64.getDecoder().decode(base64Chave);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(bytesChave);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }

    public static PrivateKey getChavePrivadaDeTexto(String base64Chave) throws Exception {
        byte[] bytesChave = Base64.getDecoder().decode(base64Chave);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(bytesChave);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }
}
