package br.gov.sp.cps.api.pixel.core.domain.entity;

import br.gov.sp.cps.api.pixel.core.domain.dto.PlantacaoDTO;
import br.gov.sp.cps.api.pixel.core.domain.dto.command.AlterarUsuarioCommand;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static br.gov.sp.cps.api.pixel.core.domain.enumeration.FuncaoUsuario.ADMIN;
import static br.gov.sp.cps.api.pixel.core.domain.enumeration.FuncaoUsuario.USUARIO;
import static org.hibernate.cfg.JdbcSettings.USER;

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

    @Column(name = "usuario_username", nullable = false)
    private String nomeUsuario;

    @Column(name = "usuario_documento", nullable = false)
    private String documento;

    @Column(name = "usuario_dt_criacao", nullable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "usuario_funcao", nullable = false)
    private String funcao;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Plantacao> plantacao;

    public static Usuario toEntity(CadastrarUsuarioCommand command){
        Usuario usuario = new Usuario();
        usuario.setNome(command.getNome());
        usuario.setEmail(command.getEmail());
        usuario.setSenha(command.getSenha());
        usuario.setNomeUsuario(command.getNomeUsuario());
        usuario.setDocumento(command.getDocumento());
        usuario.setDataCriacao(LocalDateTime.now());
        usuario.setFuncao(command.getFuncao());
        return usuario;
    }
    public static Usuario toEntity(AlterarUsuarioCommand command) {
        Usuario usuario = new Usuario();
        usuario.setId(command.getId());
        usuario.setNome(command.getNome());
        usuario.setEmail(command.getEmail());
        usuario.setDocumento(command.getDocumento());
        return usuario;
    }

    public void atualizarPlantacao(List<PlantacaoDTO>plantacaoDTO){
        if (this.plantacao == null) {
            this.plantacao = new ArrayList<>();
        }
        for (PlantacaoDTO dto : plantacaoDTO) {
            for (Plantacao plant : this.plantacao) {
                if (plant.getId().equals(dto.id())){
                    plant.setFazendaNome(dto.fazendaNome());
                    break;
                }


            }
        }
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.funcao != null) {
            return switch (funcao.toUpperCase()) {
                case "ADMIN" -> List.of(
                        new SimpleGrantedAuthority("ROLE_ADMIN"),
                        new SimpleGrantedAuthority("ROLE_USUARIO")
                );
                case "USUARIO" -> List.of(
                        new SimpleGrantedAuthority("ROLE_USUARIO")
                );
                default -> List.of();
            };
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

