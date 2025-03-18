package br.gov.sp.cps.api.pixel.core.usecase;

import br.gov.sp.cps.api.pixel.core.domain.dto.PlantacaoDTO;
import br.gov.sp.cps.api.pixel.core.domain.dto.command.CadastrarAtualizacaoPlantioCommand;
import br.gov.sp.cps.api.pixel.core.domain.dto.command.CadastrarPlantacaoCommand;
import br.gov.sp.cps.api.pixel.core.domain.entity.Especie;
import br.gov.sp.cps.api.pixel.core.domain.entity.Fazenda;
import br.gov.sp.cps.api.pixel.core.domain.entity.Plantacao;
import br.gov.sp.cps.api.pixel.core.domain.mapper.PlantacaoMapper;
import br.gov.sp.cps.api.pixel.core.domain.repository.EspecieRepository;
import br.gov.sp.cps.api.pixel.core.domain.repository.FazendaRepository;
import br.gov.sp.cps.api.pixel.core.domain.repository.PlantacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CadastrarPlantacaoUC {

    private final PlantacaoRepository plantacaoRepository;
    private final FazendaRepository fazendaRepository;
    private final EspecieRepository especieRepository;
    private final CadastrarAtualizacaoPlantioUC cadastrarAtualizacaoPlantioUC;

    @Transactional
    public PlantacaoDTO executar(CadastrarPlantacaoCommand command) {
        Fazenda fazenda = fazendaRepository.buscarPorId(command.fazendaId())
                .orElseThrow(() -> new IllegalArgumentException("Fazenda não encontrada"));
        Especie especie = especieRepository.buscarPorId(command.especieId())
                .orElseThrow(() -> new IllegalArgumentException("Espécie não encontrada"));

        Plantacao plantacao = Plantacao.toEntity(fazenda, especie, command);
        Plantacao resultado = plantacaoRepository.salvar(plantacao);

        cadastrarAtualizacaoPlantioUC.executar(CadastrarAtualizacaoPlantioCommand
                .toCommand(plantacao.getId(), command.temperaturaAmbiente(), command.temperaturaSolo(),
                        command.umidadeAmbiente(), command.umidadeSolo(), command.phSolo(), command.precipitacao(),
                        command.indiceUV()));

        return PlantacaoMapper.INSTANCE.toDTO(resultado);
    }
}
