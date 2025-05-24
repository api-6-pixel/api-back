package br.gov.sp.cps.api.pixel.core.usecase;

import br.gov.sp.cps.api.pixel.core.domain.dto.FeedbackPlantacaoDTO;
import br.gov.sp.cps.api.pixel.core.domain.entity.AtualizacaoPlantio;
import br.gov.sp.cps.api.pixel.core.domain.enumeration.CondicaoPlantacao;
import br.gov.sp.cps.api.pixel.core.domain.enumeration.PropriedadePlantacao;
import br.gov.sp.cps.api.pixel.core.domain.repository.AtualizacaoPlantioRepository;
import br.gov.sp.cps.api.pixel.core.service.ClassificadorPlantacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static br.gov.sp.cps.api.pixel.core.domain.enumeration.PropriedadePlantacao.*;

@Service
@RequiredArgsConstructor
public class ObterFeedbackPlantacaoUC extends ClassificadorPlantacaoService {

    private final AtualizacaoPlantioRepository atualizacaoPlantioRepository;

    public List<FeedbackPlantacaoDTO> executar(Long idAtualizacao){
        AtualizacaoPlantio atualizacaoPlantio = atualizacaoPlantioRepository.carregar(idAtualizacao)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Atualização não encontrada para o ID informado."));
        Map<PropriedadePlantacao, Number> propriedade = obterValorPropriedade(atualizacaoPlantio);

        return Arrays.stream(PropriedadePlantacao.values()).map(propriedadePlantacao -> {
            FeedbackPlantacaoDTO feedbackPlantacaoDTO = new FeedbackPlantacaoDTO();
            CondicaoPlantacao condicaoPlantacao = classificarFaixa((Double) propriedade.get(propriedadePlantacao),
                    propriedadePlantacao.getRuimMinimo(), propriedadePlantacao.getAceitavelMinimo(),
                    propriedadePlantacao.getAceitavelMaximo(), propriedadePlantacao.getRuimMaximo());
            feedbackPlantacaoDTO.setPropriedade(propriedadePlantacao.getDescricao());
            feedbackPlantacaoDTO.setMelhorar(condicaoPlantacao != CondicaoPlantacao.IDEAL);
            feedbackPlantacaoDTO.setValorAtual((double) propriedade.get(propriedadePlantacao));
            feedbackPlantacaoDTO.setStatus(condicaoPlantacao.name());

            return feedbackPlantacaoDTO;
        }).collect(Collectors.toList());
    }

    private Map<PropriedadePlantacao, Number> obterValorPropriedade(AtualizacaoPlantio atualizacaoPlantio){
        Map<PropriedadePlantacao, Number> map = new HashMap<>();
        map.put(UMIDADE_SOLO, atualizacaoPlantio.getUmidadeSolo());
        map.put(TEMPERATURA_AMBIENTE, atualizacaoPlantio.getTemperaturaAmbiente());
        map.put(TEMPERATURA_SOLO, atualizacaoPlantio.getTemperaturaSolo());
        map.put(UMIDADE_AMBIENTE, atualizacaoPlantio.getUmidadeSolo());
        map.put(INDICE_UV, atualizacaoPlantio.getIndiceUV());
        map.put(PH_SOLO, atualizacaoPlantio.getPhSolo());
        return map;
    }
}
