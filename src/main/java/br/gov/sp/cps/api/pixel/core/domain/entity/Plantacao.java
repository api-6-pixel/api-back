package br.gov.sp.cps.api.pixel.core.domain.entity;

import br.gov.sp.cps.api.pixel.core.domain.dto.command.CadastrarPlantacaoCommand;
import br.gov.sp.cps.api.pixel.core.domain.enumeration.StatusPlantacao;
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
@Table(name = "tb_plantacao")
public class Plantacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plantacao_id")
    private Long id;

    @Column(name = "plantacao_fazenda", nullable = false)
    private String fazendaNome;

    @Column(name = "plantacao_especie", nullable = false)
    private String especieNome;

    @Column(name = "plantacao_area", nullable = false)
    private Double areaPlantada;

    @Column(name = "plantacao_data", nullable = false)
    private LocalDateTime dataPlantio;

    @Column(name = "plantacao_custo", nullable = false)
    private Double custoEsperado;

    @Enumerated(EnumType.STRING)
    @Column(name = "plantacao_status", nullable = false)
    private StatusPlantacao status;

    public Plantacao(String fazendaNome,
                     String especieNome,
                     Double areaPlantada,
                     LocalDateTime dataPlantio,
                     Double custoEsperado,
                     StatusPlantacao status){
        this.fazendaNome = fazendaNome;
        this.especieNome = especieNome;
        this.areaPlantada = areaPlantada;
        this.dataPlantio = dataPlantio;
        this.custoEsperado = custoEsperado;
        this.status = status;
    }

    public static Plantacao toEntity(CadastrarPlantacaoCommand command) {
        return new Plantacao(command.fazendaNome(), command.especieNome(), command.areaPlantada(),
                LocalDateTime.now(), command.custoEsperado(), command.status());
    }
}
