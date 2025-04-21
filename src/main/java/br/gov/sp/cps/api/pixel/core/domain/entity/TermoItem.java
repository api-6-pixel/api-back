package br.gov.sp.cps.api.pixel.core.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_cad_termo_item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TermoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @ManyToOne
    @JoinColumn(name = "termo_codigo", nullable = false)
    private Termo termo;

    private String descricao;
    private boolean obrigatorio;
}
