package br.gov.sp.cps.api.pixel.core.domain.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tb_portabilidade")
public class ChavePortabilidade {

    @Column(name = "portabilidade_minha_chave_privada", nullable = false, length = 5000)
    private String minhaChavePrivada;

    @Column(name = "portabilidade_lib_chave_publica", nullable = false, length = 5000)
    private String libChavePublica;

    @Column(name = "portabilidade_tempo_expiracao", nullable = false)
    private LocalDateTime tempoExp; 

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "portabilidade_id", nullable = false)
    private Long id;

    public ChavePortabilidade(String minhaChavePrivada, String libChavePublica, LocalDateTime tempoExp) {
        this.minhaChavePrivada = minhaChavePrivada;
        this.libChavePublica = libChavePublica;
        this.tempoExp = tempoExp;
    }
}
