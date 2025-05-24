package br.gov.sp.cps.api.pixel.core.usecase;

import br.gov.sp.cps.api.pixel.core.domain.dto.AtualizacaoPlantioDTO;
import br.gov.sp.cps.api.pixel.core.domain.dto.EnviarDadosAtualizacaoDTO;
import br.gov.sp.cps.api.pixel.core.domain.dto.command.CadastrarAtualizacaoPlantioCommand;
import br.gov.sp.cps.api.pixel.core.domain.entity.AtualizacaoPlantio;
import br.gov.sp.cps.api.pixel.core.domain.entity.Plantacao;
import br.gov.sp.cps.api.pixel.core.domain.enumeration.CondicaoPlantacao;
import br.gov.sp.cps.api.pixel.core.domain.mapper.AtualizacaoPlantioMapper;
import br.gov.sp.cps.api.pixel.core.domain.repository.AtualizacaoPlantioRepository;
import br.gov.sp.cps.api.pixel.core.domain.repository.EnviarDadosAtualizacaoRepository;
import br.gov.sp.cps.api.pixel.core.domain.repository.PlantacaoRepository;
import br.gov.sp.cps.api.pixel.core.service.ClassificadorPlantacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static br.gov.sp.cps.api.pixel.core.domain.enumeration.PropriedadePlantacao.*;

@Service
@RequiredArgsConstructor
public class CadastrarAtualizacaoPlantioUC extends ClassificadorPlantacaoService {

    private final AtualizacaoPlantioRepository atualizacaoPlantioRepository;
    private final PlantacaoRepository plantacaoRepository;
    private final AtualizacaoPlantioMapper mapper;
    private final EnviarDadosAtualizacaoRepository enviarDadosAtualizacaoRepository;

    @Transactional
    public AtualizacaoPlantioDTO executar(CadastrarAtualizacaoPlantioCommand command){
        Plantacao plantacao = plantacaoRepository.buscarPorFazenda(command.fazendaNome())
                .orElseThrow(() -> new IllegalArgumentException("Fazenda não encontrada"));

        AtualizacaoPlantio atualizacaoPlantio = AtualizacaoPlantio.toEntity(plantacao, command);
        obterCondicaoPlatantacao(atualizacaoPlantio);
        AtualizacaoPlantio resultado = atualizacaoPlantioRepository.salvar(atualizacaoPlantio);

        enviarDadosAtualizacaoRepository.enviar(EnviarDadosAtualizacaoDTO.toDto(resultado, command.custoEsperado()));

        return mapper.toDTO(resultado);
    }

    private void obterCondicaoPlatantacao(AtualizacaoPlantio atualizacaoPlantio){
        int ideal = 0;
        int aceitavel = 0;
        int ruim = 0;

        CondicaoPlantacao[] condicoes = {
                classificarFaixa(atualizacaoPlantio.getUmidadeSolo(), UMIDADE_SOLO.getRuimMinimo(), UMIDADE_SOLO.getAceitavelMinimo(),
                        UMIDADE_SOLO.getAceitavelMaximo(), UMIDADE_SOLO.getRuimMaximo()),
                classificarFaixa(atualizacaoPlantio.getTemperaturaAmbiente(), TEMPERATURA_AMBIENTE.getRuimMinimo(), TEMPERATURA_AMBIENTE.getAceitavelMinimo(),
                        TEMPERATURA_AMBIENTE.getAceitavelMaximo(), TEMPERATURA_AMBIENTE.getRuimMaximo()),
                classificarFaixa(atualizacaoPlantio.getTemperaturaSolo(), TEMPERATURA_SOLO.getRuimMinimo(), TEMPERATURA_SOLO.getAceitavelMinimo(),
                        TEMPERATURA_SOLO.getAceitavelMaximo(), TEMPERATURA_SOLO.getRuimMaximo()),
                classificarFaixa(atualizacaoPlantio.getUmidadeAmbiente(), UMIDADE_AMBIENTE.getRuimMinimo(), UMIDADE_AMBIENTE.getAceitavelMinimo(),
                        UMIDADE_AMBIENTE.getAceitavelMaximo(), UMIDADE_AMBIENTE.getRuimMaximo()),
                classificarFaixa(atualizacaoPlantio.getIndiceUV(), INDICE_UV.getRuimMinimo(), INDICE_UV.getAceitavelMinimo(),
                        INDICE_UV.getAceitavelMaximo(), INDICE_UV.getRuimMaximo()),
                classificarFaixa(atualizacaoPlantio.getPhSolo(), PH_SOLO.getRuimMinimo(), PH_SOLO.getAceitavelMinimo(),
                        PH_SOLO.getAceitavelMaximo(), PH_SOLO.getRuimMaximo())
        };

        for (CondicaoPlantacao cond : condicoes) {
            switch (cond) {
                case IDEAL -> ideal++;
                case ACEITAVEL -> aceitavel++;
                case RUIM -> ruim++;
            }
        }

        CondicaoPlantacao condicaoFinal;

        if(ideal == 6){
            condicaoFinal = CondicaoPlantacao.IDEAL;
        }else if(ruim >= 3){
            condicaoFinal = CondicaoPlantacao.RUIM;
        } else {
            condicaoFinal = CondicaoPlantacao.ACEITAVEL;
        }

        atualizacaoPlantio.setCondicaoPlantacao(condicaoFinal);
    }

}
