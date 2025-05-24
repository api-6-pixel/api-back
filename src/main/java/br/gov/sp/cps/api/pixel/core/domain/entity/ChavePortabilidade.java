package br.gov.sp.cps.api.pixel.core.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.GeneratedValue;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tb_portabilidade")
public class ChavePortabilidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "portabilidade_id", nullable = false)
    private Long id;

    @Column(name = "portabilidade_minha_chave_privada", nullable = false, length = 5000)
    private String minhaChavePrivada;

    @Column(name = "portabilidade_lib_chave_publica", nullable = false, length = 5000)
    private String libChavePublica;

    @Column(name = "portabilidade_tempo_expiracao", nullable = true)
    private LocalDateTime tempoExp; 

    @Column(name = "portabilidade_hash", nullable = true)
    private String hashConfirmacao; 

    @Column(name = "portabilidade_autenticado", nullable = false)
    private Boolean autenticado = false; 

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = true)
    private Usuario usuario; 


    public ChavePortabilidade(String minhaChavePrivada, String libChavePublica, LocalDateTime tempoExp) {
        this.minhaChavePrivada = minhaChavePrivada;
        this.libChavePublica = libChavePublica;
        this.tempoExp = tempoExp;
    }
}
