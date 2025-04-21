package br.gov.sp.cps.api.pixel.core.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_cad_termo_item_aceite")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TermoItemAceite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @ManyToOne
    @JoinColumn(name = "termo_aceite_codigo", nullable = false)
    private TermoItemAceiteUsuarioHistorico termoAceiteUsuarioHistorico;

    @ManyToOne
    @JoinColumn(name = "termo_item_codigo", nullable = false)
    private TermoItem termoItem;

    private boolean aceito;
}
