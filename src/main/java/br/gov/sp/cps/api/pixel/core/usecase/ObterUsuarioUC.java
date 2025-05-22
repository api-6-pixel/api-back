package br.gov.sp.cps.api.pixel.core.usecase;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.ByteArrayInputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.spec.RSAPublicKeySpec;
import java.time.LocalDateTime;
import java.util.Base64;

import javax.crypto.Cipher;

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.parsers.DocumentBuilderFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.gov.sp.cps.api.pixel.core.domain.dto.PortabilidadeDTO;
import br.gov.sp.cps.api.pixel.core.domain.dto.UsuarioDTO;
import br.gov.sp.cps.api.pixel.core.domain.repository.CriptografiaRepository;
import br.gov.sp.cps.api.pixel.core.domain.dto.command.ObterUsuarioIDCommand;
import br.gov.sp.cps.api.pixel.core.domain.entity.ChavePortabilidade;
import br.gov.sp.cps.api.pixel.core.domain.repository.PortabilidadeRepository;
import lombok.RequiredArgsConstructor;
import org.w3c.dom.Document;


@Service
@RequiredArgsConstructor
public class ObterUsuarioUC {
    
    private final CarregarUsuarioUC usuarioUc;
    private final PortabilidadeRepository portabilidadeRepository;

    //Usuario deveria ser pego do auth e aqui nao deveria ta nada
    public PortabilidadeDTO executar (ObterUsuarioIDCommand command) throws Exception{
        ChavePortabilidade chave = portabilidadeRepository.buscarPorId(command.getClientID())
        .orElseThrow(() -> new RuntimeException("Nenhuma chave encontrada para o ID informado."));

        if (!chave.getAutenticado()) {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(chave.getUsuario().getEmail().getBytes());
            String encoded = Base64.getEncoder().encodeToString(hash);
            chave.setHashConfirmacao(encoded);
            portabilidadeRepository.salvar(chave);
            //Notificar usuario
            return new PortabilidadeDTO("Solicitado autorizacao para o usuario", null, null);
        } 
        
        LocalDateTime agora = LocalDateTime.now();
        if (agora.isAfter(chave.getTempoExp())) {
            return new PortabilidadeDTO("Chave expirada", null, null);
        } 

        PrivateKey chavePrivada = getChavePrivadaDeTexto(chave.getMinhaChavePrivada());
        byte[] key = descriptografar(command.getAesKey(), chavePrivada);
        byte[] iv = descriptografar(command.getAesIv(), chavePrivada);

        Cipher cipherDec = createAESCipher(key, iv, Cipher.DECRYPT_MODE);
        byte[] decryptedBytes = cipherDec.doFinal(Base64.getDecoder().decode(command.getUsuarioID()));
        String usuarioID = new String(decryptedBytes, StandardCharsets.UTF_8);
        
        UsuarioDTO usuario = usuarioUc.executar(Long.parseLong(usuarioID));
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String usuarioJson = objectMapper.writeValueAsString(usuario);

        Cipher cipherEnc = createAESCipher(key, iv, Cipher.ENCRYPT_MODE);
        byte[] encryptedBytes = cipherEnc.doFinal(usuarioJson.getBytes(StandardCharsets.UTF_8));
        String dados = Base64.getEncoder().encodeToString(encryptedBytes);
        
        PublicKey chavePublica = getChavePublicaDeTexto(chave.getLibChavePublica());
        byte[] novaChave = criptografar(Base64.getEncoder().encodeToString(key), chavePublica);
        byte[] novaIv = criptografar(Base64.getEncoder().encodeToString(iv), chavePublica);

        return new PortabilidadeDTO(dados, Base64.getEncoder().encodeToString(novaChave),Base64.getEncoder().encodeToString(novaIv));
    } 

   public static Cipher createAESCipher(byte[] key, byte[] iv, int mode) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding"); // or "AES/CBC/PKCS7Padding" with BouncyCastle
        cipher.init(mode, secretKey, ivSpec);
        
        return cipher;
    }

    public static byte[] descriptografar(String textoBase64, PrivateKey chavePrivada) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, chavePrivada);
        byte[] bytesDescriptografados = cipher.doFinal(Base64.getDecoder().decode(textoBase64));
        return bytesDescriptografados;
    }

   public static byte[] criptografar(String texto, PublicKey chavePublica) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, chavePublica);
        return cipher.doFinal(texto.getBytes());
    }

    public static PrivateKey getChavePrivadaDeTexto(String base64Chave) throws Exception {
        byte[] bytesChave = Base64.getDecoder().decode(base64Chave);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(bytesChave);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }

    public static PublicKey getChavePublicaDeTexto(String base64Chave) throws Exception {
         Document doc = DocumentBuilderFactory.newInstance()
            .newDocumentBuilder()
            .parse(new ByteArrayInputStream(Base64.getDecoder().decode(base64Chave.getBytes())));

        String modulusBase64 = doc.getElementsByTagName("Modulus").item(0).getTextContent();
        String exponentBase64 = doc.getElementsByTagName("Exponent").item(0).getTextContent();

        BigInteger modulus = new BigInteger(1, Base64.getDecoder().decode(modulusBase64));
        BigInteger exponent = new BigInteger(1, Base64.getDecoder().decode(exponentBase64));

        RSAPublicKeySpec spec = new RSAPublicKeySpec(modulus, exponent);
        return KeyFactory.getInstance("RSA").generatePublic(spec);     
    }

}
