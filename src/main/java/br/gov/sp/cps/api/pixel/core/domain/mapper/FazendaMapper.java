package br.gov.sp.cps.api.pixel.core.domain.mapper;

import br.gov.sp.cps.api.pixel.core.domain.dto.FazendaDTO;
import br.gov.sp.cps.api.pixel.core.domain.entity.Fazenda;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FazendaMapper {
    FazendaDTO toDTO(Fazenda fazenda);
}
