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
@Table(name = "tb_portabildade_acessos")
public class PortabilidadeAcessos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "portabildade_acessos_id", nullable = false)
    private Long id;


    @Column(name = "portabildade_acessos_client_id", nullable = false)
    private String clientId;

    @Column(name = "portabildade_acessos_client_secret", nullable = false)
    private String clientSecret;
}
