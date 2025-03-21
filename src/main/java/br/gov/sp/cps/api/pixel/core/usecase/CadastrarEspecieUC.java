package br.gov.sp.cps.api.pixel.core.usecase;

import br.gov.sp.cps.api.pixel.core.domain.dto.EspecieDTO;
import br.gov.sp.cps.api.pixel.core.domain.dto.command.CadastrarEspecieCommand;
import br.gov.sp.cps.api.pixel.core.domain.entity.Especie;
import br.gov.sp.cps.api.pixel.core.domain.mapper.EspecieMapper;
import br.gov.sp.cps.api.pixel.core.domain.repository.EspecieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CadastrarEspecieUC {

    private final EspecieRepository especieRepository;
    private final EspecieMapper especieMapper;

    @Transactional
    public EspecieDTO executar(CadastrarEspecieCommand command){
        Especie especie = Especie.toEntity(command);
        Especie resultado = especieRepository.salvar(especie);
        return especieMapper.toDto(resultado);
    }
}
