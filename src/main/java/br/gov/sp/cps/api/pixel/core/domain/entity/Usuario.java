package br.gov.sp.cps.api.pixel.core.domain.entity;

import br.gov.sp.cps.api.pixel.core.domain.dto.command.CadastrarUsuarioCommand;
import br.gov.sp.cps.api.pixel.core.domain.enumeration.FuncaoUsuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tb_usuario")
public class Usuario implements UserDetails {

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

    @Column(name = "usuario_funcao", nullable = false)
    private String funcao;

    public static Usuario toEntity(CadastrarUsuarioCommand command){
        Usuario usuario = new Usuario();
        usuario.setNome(command.getNome());
        usuario.setEmail(command.getEmail());
        usuario.setSenha(command.getSenha());
        usuario.setDocumento(command.getDocumento());
        usuario.setDataCriacao(LocalDateTime.now());
        usuario.setFuncao(command.getFuncao());
        return usuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.funcao != null) {
            return List.of(new SimpleGrantedAuthority("USUARIO"));
        }
        return List.of();
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}