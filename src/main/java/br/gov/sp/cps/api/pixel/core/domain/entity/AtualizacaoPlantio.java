package br.gov.sp.cps.api.pixel.core.domain.entity;

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
@Table(name = "tb_atualizacao")
public class AtualizacaoPlantio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "atualizacao_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plantacao_id", nullable = false)
    private Plantacao plantacao;

    @Column(name = "atualizacao_temperatura_ambiente", nullable = false)
    private Double temperaturaAmbiente;

    @Column(name = "atualizacao_temperatura_solo", nullable = false)
    private Double temperaturaSolo;

    @Column(name = "atualizacao_umidade_ambiente", nullable = false)
    private Double umidadeAmbiente;

    @Column(name = "atualizacao_umidade_solo", nullable = false)
    private Double umidadeSolo;

    @Column(name = "atualizacao_ph_solo", nullable = false)
    private Double phSolo;

    @Column(name = "atualizacao_precipitacao", nullable = false)
    private Double precipitacao;

    @Column(name = "atualizacao_indice_uv", nullable = false)
    private Double indiceUV;

    @Column(name = "atualizacao_registro", nullable = false)
    private LocalDateTime dataRegistro;

    public AtualizacaoPlantio(Plantacao plantacao, Double temperaturaAmbiente, Double temperaturaSolo,
                              Double umidadeAmbiente, Double umidadeSolo, Double phSolo, Double precipitacao,
                              Double indiceUV, LocalDateTime dataRegistro) {
        this.plantacao = plantacao;
        this.temperaturaAmbiente = temperaturaAmbiente;
        this.temperaturaSolo = temperaturaSolo;
        this.umidadeAmbiente = umidadeAmbiente;
        this.umidadeSolo = umidadeSolo;
        this.phSolo = phSolo;
        this.precipitacao = precipitacao;
        this.indiceUV = indiceUV;
        this.dataRegistro = dataRegistro;
    }
}
