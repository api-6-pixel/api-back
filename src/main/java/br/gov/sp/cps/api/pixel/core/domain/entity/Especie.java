package br.gov.sp.cps.api.pixel.core.domain.entity;

import br.gov.sp.cps.api.pixel.core.domain.dto.command.CadastrarEspecieCommand;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tb_especie")
public class Especie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "especie_id")
    private Long id;

    @Column(name = "especie_nome", nullable = false, unique = true)
    private String nome;

    @Column(name = "especie_descricao", nullable = false)
    private String descricao;

    /*
    @OneToMany(mappedBy = "especie", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Plantacao> plantacoes;
     */

    public Especie(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    public static Especie toEntity(CadastrarEspecieCommand command){
        return new Especie(command.nome(), command.descricao());
    }
}
