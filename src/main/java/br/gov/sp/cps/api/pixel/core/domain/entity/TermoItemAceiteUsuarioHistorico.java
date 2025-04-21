package br.gov.sp.cps.api.pixel.core.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_cad_termo_item_aceite_usuario_historico")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TermoItemAceiteUsuarioHistorico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @ManyToOne
    @JoinColumn(name = "usuario_codigo", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "termo_codigo", nullable = false)
    private Termo termo;

    private LocalDateTime dataAceite;
    private LocalDateTime dataAlteracao;
}
