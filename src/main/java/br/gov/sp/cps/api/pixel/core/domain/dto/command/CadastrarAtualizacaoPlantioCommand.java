package br.gov.sp.cps.api.pixel.core.domain.dto.command;

public record CadastrarAtualizacaoPlantioCommand(
        Long plantacaoId,
        Double temperaturaAmbiente,
        Double temperaturaSolo,
        Double umidadeAmbiente,
        Double umidadeSolo,
        Double phSolo,
        Double indiceUV) {

    public static CadastrarAtualizacaoPlantioCommand toCommand(Long plantacaoId,
                                                        Double temperaturaAmbiente,
                                                        Double temperaturaSolo,
                                                        Double umidadeAmbiente,
                                                        Double umidadeSolo,
                                                        Double phSolo,
                                                        Double indiceUV){
        return new CadastrarAtualizacaoPlantioCommand(plantacaoId, temperaturaAmbiente, temperaturaSolo,
                umidadeAmbiente, umidadeSolo, phSolo, indiceUV);
    }
}
