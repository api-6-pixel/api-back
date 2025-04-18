package br.gov.sp.cps.api.pixel.core.domain.entity;

import br.gov.sp.cps.api.pixel.core.domain.dto.command.CadastrarFazendaCommand;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tb_fazenda")
public class Fazenda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fazenda_id")
    private Long id;

    @Column(name = "fazenda_nome", nullable = false)
    private String nome;

    @Column(name = "fazenda_localizacao", nullable = false)
    private String localizacao;

    @Column(name = "fazenda_area", nullable = false)
    private Double areaTotal;

    @Column(name = "fazenda_criacao", nullable = false)
    private LocalDateTime dataCriacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    public Fazenda(String nome, String localizacao, Double areaTotal, LocalDateTime dataCriacao, Usuario usuario) {
        this.nome = nome;
        this.localizacao = localizacao;
        this.areaTotal = areaTotal;
        this.dataCriacao = dataCriacao;
        this.usuario = usuario;
    }

    public static Fazenda toEntity(CadastrarFazendaCommand command, Usuario usuario) {
        return new Fazenda(command.nome(), command.localizacao(), command.areaTotal(), LocalDateTime.now(), usuario);
    }
}
