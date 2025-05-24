package br.gov.sp.cps.api.pixel.core.usecase;

import br.gov.sp.cps.api.pixel.core.domain.dto.StatusDTO;
import br.gov.sp.cps.api.pixel.core.domain.repository.AtualizacaoPlantioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarregarStatusPlantacaoUC {

    private final AtualizacaoPlantioRepository atualizacaoPlantioRepository;

    public List<StatusDTO> executar(){
        return atualizacaoPlantioRepository.carregar().stream().map(atualizacaoPlantio -> {
            StatusDTO statusDTO = new StatusDTO();
            statusDTO.setStatus(atualizacaoPlantio.getCondicaoPlantacao());
            statusDTO.setData(atualizacaoPlantio.getDataRegistro().toLocalDate());
            return statusDTO;
        }).collect(Collectors.toList());
    }
}
