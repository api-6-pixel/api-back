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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fazenda_id", nullable = false)
    private Fazenda fazenda;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "especie_id", nullable = false)
    private Especie especie;

    @Column(name = "plantacao_area", nullable = false)
    private Double areaPlantada;

    @Column(name = "plantacao_solo", nullable = false)
    private String tipoSolo;

    @Column(name = "plantacao_data", nullable = false)
    private LocalDateTime dataPlantio;

    @Column(name = "plantacao_custo", nullable = false)
    private Double custoEsperado;

    @Enumerated(EnumType.STRING)
    @Column(name = "plantacao_status", nullable = false)
    private StatusPlantacao status;

    public Plantacao(Fazenda fazenda,
                     Especie especie,
                     Double areaPlantada,
                     String tipoSolo,
                     LocalDateTime dataPlantio,
                     Double custoEsperado,
                     StatusPlantacao status){
        this.fazenda = fazenda;
        this.especie = especie;
        this.areaPlantada = areaPlantada;
        this.tipoSolo = tipoSolo;
        this.dataPlantio = dataPlantio;
        this.custoEsperado = custoEsperado;
        this.status = status;
    }

    public static Plantacao toEntity(Fazenda fazenda, Especie especie, CadastrarPlantacaoCommand command) {
        return new Plantacao(fazenda, especie, command.areaPlantada(), command.tipoSolo(),
                LocalDateTime.now(), command.custoEsperado(), command.status());
    }
}
