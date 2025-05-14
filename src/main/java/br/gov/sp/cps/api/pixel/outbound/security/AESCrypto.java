package br.gov.sp.cps.api.pixel.outbound.security;

import br.gov.sp.cps.api.pixel.core.domain.dto.command.AlterarUsuarioCommand;
import br.gov.sp.cps.api.pixel.core.domain.dto.command.CadastrarUsuarioCommand;
import br.gov.sp.cps.api.pixel.core.domain.entity.Usuario;
import br.gov.sp.cps.api.pixel.core.domain.repository.CriptografiaRepository;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;

@Component
public class AESCrypto implements CriptografiaRepository {

    public SecretKey gerarChave() throws Exception {
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(256);
        return generator.generateKey();
    }
    
    public String encriptar(String plainText, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public String descriptografar(String encryptedText, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        return new String(decryptedBytes);
    }

    public Object getObjectEncriptografado(CadastrarUsuarioCommand command, SecretKey secretKey) throws Exception {
        command.setNome(encriptar(command.getNome(), secretKey));
        command.setEmail(encriptar(command.getEmail(), secretKey));
        command.setSenha(command.getSenha());
        command.setDocumento(encriptar(command.getDocumento(), secretKey));
        command.setFuncao(encriptar(command.getFuncao(), secretKey));
        return command;
    }

    @Override
    public Object getObjectEncriptografado(AlterarUsuarioCommand command, SecretKey secretKey) throws Exception {
        command.setNome(encriptar(command.getNome(), secretKey));
        command.setEmail(command.getEmail());
        command.setDocumento(encriptar(command.getDocumento(), secretKey));
        return command;
    }

    public Object getObjectDescriptografado(Usuario usuario, SecretKey secretKey) throws Exception {
        usuario.setNome(descriptografar(usuario.getNome(), secretKey));
        usuario.setDocumento(descriptografar(usuario.getDocumento(), secretKey));
        usuario.setEmail(descriptografar(usuario.getEmail(), secretKey));

        return usuario;
    }
}
