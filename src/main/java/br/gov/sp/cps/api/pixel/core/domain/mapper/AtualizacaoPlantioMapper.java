package br.gov.sp.cps.api.pixel.core.domain.mapper;


import br.gov.sp.cps.api.pixel.core.domain.dto.AtualizacaoPlantioDTO;
import br.gov.sp.cps.api.pixel.core.domain.entity.AtualizacaoPlantio;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AtualizacaoPlantioMapper {
    AtualizacaoPlantioMapper INSTANCE = Mappers.getMapper(AtualizacaoPlantioMapper.class);

    @Mapping(source = "plantacao.id", target = "plantacaoId")
    AtualizacaoPlantioDTO toDTO(AtualizacaoPlantio atualizacaoPlantio);
}
