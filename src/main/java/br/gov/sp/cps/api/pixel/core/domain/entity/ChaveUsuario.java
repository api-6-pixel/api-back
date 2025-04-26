package br.gov.sp.cps.api.pixel.core.domain.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "chave_usuario")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChaveUsuario {

    @Id
    private String id;
    private Long idUsuario;
    private String chave;

    public ChaveUsuario(Long idUsuario, String secretKey) {
        this.idUsuario = idUsuario;
        this.chave = secretKey;
    }
}
