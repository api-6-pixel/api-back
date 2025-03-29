package br.gov.sp.cps.api.pixel.core.usecase;

import br.gov.sp.cps.api.pixel.core.domain.dto.FazendaDTO;
import br.gov.sp.cps.api.pixel.core.domain.dto.command.CadastrarFazendaCommand;
import br.gov.sp.cps.api.pixel.core.domain.entity.Fazenda;
import br.gov.sp.cps.api.pixel.core.domain.mapper.FazendaMapper;
import br.gov.sp.cps.api.pixel.core.domain.repository.FazendaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CadastrarFazendaUC {

    private final FazendaRepository fazendaRepository;
    private final FazendaMapper fazendaMapper;

    @Transactional
    public FazendaDTO executar(CadastrarFazendaCommand command){
        Fazenda fazenda = Fazenda.toEntity(command);
        Fazenda resultado = fazendaRepository.salvar(fazenda);
        return fazendaMapper.toDTO(resultado);
    }
}
