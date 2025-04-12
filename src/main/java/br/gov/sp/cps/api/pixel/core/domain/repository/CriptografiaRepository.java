package br.gov.sp.cps.api.pixel.core.domain.repository;

import br.gov.sp.cps.api.pixel.core.domain.dto.command.CadastrarUsuarioCommand;

import javax.crypto.SecretKey;

public interface CriptografiaRepository {

    SecretKey gerarChave() throws Exception;
    String encriptar(String plainText, SecretKey key) throws Exception;
    String descriptografar(String encryptedText, SecretKey key) throws Exception;
    Object getObjectEncriptografado(CadastrarUsuarioCommand command, SecretKey secretKey) throws Exception;
}
