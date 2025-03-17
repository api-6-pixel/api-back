package br.gov.sp.cps.api.pixel.inbound;

import br.gov.sp.cps.api.pixel.core.domain.dto.PlantacaoDTO;
import br.gov.sp.cps.api.pixel.core.domain.dto.command.CadastrarPlantacaoCommand;
import br.gov.sp.cps.api.pixel.core.usecase.CadastrarPlantacaoUC;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/plantacoes")
@RequiredArgsConstructor
public class PlantacaoController {

    private final CadastrarPlantacaoUC cadastrarPlantacaoUC;

    @PostMapping
    public ResponseEntity<PlantacaoDTO> cadastrarPlantacao(@RequestBody CadastrarPlantacaoCommand command){
        PlantacaoDTO plantacao = cadastrarPlantacaoUC.executar(command);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(plantacao.id())
                .toUri();
        return ResponseEntity.created(location).body(plantacao);
    }
}
