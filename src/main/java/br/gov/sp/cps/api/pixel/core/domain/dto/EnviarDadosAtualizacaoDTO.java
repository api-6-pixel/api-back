package br.gov.sp.cps.api.pixel.core.domain.dto;

import br.gov.sp.cps.api.pixel.core.domain.entity.AtualizacaoPlantio;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnviarDadosAtualizacaoDTO {

    private String fazendaNome;
    private Double umidadeSolo;
    private Double temperaturaAmbiente;
    private Double temperaturaSolo;
    private Double umidadeAmbiente;
    private Double indiceUV;
    private Double phSolo;
    private Double custoEsperado;

    public static EnviarDadosAtualizacaoDTO toDto(AtualizacaoPlantio entity, Double custoEsperado){
        EnviarDadosAtualizacaoDTO dto = new EnviarDadosAtualizacaoDTO();
        dto.setFazendaNome(entity.getPlantacao().getFazendaNome());
        dto.setUmidadeSolo(entity.getUmidadeSolo());
        dto.setTemperaturaAmbiente(entity.getTemperaturaAmbiente());
        dto.setTemperaturaSolo(entity.getTemperaturaSolo());
        dto.setUmidadeAmbiente(entity.getUmidadeAmbiente());
        dto.setIndiceUV(entity.getIndiceUV());
        dto.setPhSolo(entity.getPhSolo());
        dto.setCustoEsperado(custoEsperado);
        return dto;
    }
}
