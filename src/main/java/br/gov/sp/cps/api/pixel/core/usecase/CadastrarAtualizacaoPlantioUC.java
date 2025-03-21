package br.gov.sp.cps.api.pixel.core.usecase;

import br.gov.sp.cps.api.pixel.core.domain.dto.AtualizacaoPlantioDTO;
import br.gov.sp.cps.api.pixel.core.domain.dto.command.CadastrarAtualizacaoPlantioCommand;
import br.gov.sp.cps.api.pixel.core.domain.entity.AtualizacaoPlantio;
import br.gov.sp.cps.api.pixel.core.domain.entity.Plantacao;
import br.gov.sp.cps.api.pixel.core.domain.mapper.AtualizacaoPlantioMapper;
import br.gov.sp.cps.api.pixel.core.domain.repository.AtualizacaoPlantioRepository;
import br.gov.sp.cps.api.pixel.core.domain.repository.PlantacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CadastrarAtualizacaoPlantioUC {

    private final AtualizacaoPlantioRepository atualizacaoPlantioRepository;
    private final PlantacaoRepository plantacaoRepository;
    private final AtualizacaoPlantioMapper mapper;

    @Transactional
    public AtualizacaoPlantioDTO executar(CadastrarAtualizacaoPlantioCommand command){
        Plantacao plantacao = plantacaoRepository.buscarPorId(command.plantacaoId())
                .orElseThrow(() -> new IllegalArgumentException("Plantação não encontrada"));

        AtualizacaoPlantio atualizacaoPlantio = AtualizacaoPlantio.toEntity(plantacao, command);
        AtualizacaoPlantio resultado = atualizacaoPlantioRepository.salvar(atualizacaoPlantio);

        return mapper.toDTO(resultado);
    }
}
