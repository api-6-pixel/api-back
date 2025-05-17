package br.gov.sp.cps.api.pixel.core.domain.mapper;

import br.gov.sp.cps.api.pixel.core.domain.dto.PlantacaoDTO;
import br.gov.sp.cps.api.pixel.core.domain.entity.Plantacao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PlantacaoMapper {
    @Mapping(source = "idAtualizacao", target = "idAtualizacao")
    PlantacaoDTO toDTO(Plantacao plantacao, Long idAtualizacao);
}
