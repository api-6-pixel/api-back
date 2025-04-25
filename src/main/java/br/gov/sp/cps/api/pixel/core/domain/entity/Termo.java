package br.gov.sp.cps.api.pixel.core.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_cad_termo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Termo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    private String titulo;
    private String descricao;
    private LocalDateTime dataCriacao;
    private String versao;
}
