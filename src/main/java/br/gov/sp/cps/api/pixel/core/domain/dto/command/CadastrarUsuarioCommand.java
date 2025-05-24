package br.gov.sp.cps.api.pixel.core.domain.dto.command;

import lombok.Data;

@Data
public class CadastrarUsuarioCommand {
    private String nome;
    private String email;
    private String nomeUsuario;
    private String senha;
    private String documento;
    private String funcao;
}
