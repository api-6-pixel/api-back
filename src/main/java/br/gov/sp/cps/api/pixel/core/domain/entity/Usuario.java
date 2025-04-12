package br.gov.sp.cps.api.pixel.core.domain.entity;

import br.gov.sp.cps.api.pixel.core.domain.dto.command.CadastrarUsuarioCommand;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tb_usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id")
    private Long id;

    @Column(name = "usuario_nome", nullable = false)
    private String nome;

    @Column(name = "usuario_email", nullable = false)
    private String email;

    @Column(name = "usuario_senha", nullable = false)
    private String senha;

    @Column(name = "usuario_documento", nullable = false)
    private String documento;

    @Column(name = "usuario_dt_criacao", nullable = false)
    private LocalDateTime dataCriacao;

    public static Usuario toEntity(CadastrarUsuarioCommand command){
        Usuario usuario = new Usuario();
        usuario.setNome(command.getNome());
        usuario.setEmail(command.getEmail());
        usuario.setSenha(command.getSenha());
        usuario.setDocumento(command.getDocumento());
        usuario.setDataCriacao(LocalDateTime.now());
        return usuario;
    }
}
