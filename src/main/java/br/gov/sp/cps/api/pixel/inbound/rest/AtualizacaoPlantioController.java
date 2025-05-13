package br.gov.sp.cps.api.pixel.inbound.rest;

import br.gov.sp.cps.api.pixel.core.domain.dto.AtualizacaoPlantioDTO;
import br.gov.sp.cps.api.pixel.core.domain.dto.command.CadastrarAtualizacaoPlantioCommand;
import br.gov.sp.cps.api.pixel.core.usecase.CadastrarAtualizacaoPlantioUC;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/atualizacoes")
@RequiredArgsConstructor
public class AtualizacaoPlantioController {

    private final CadastrarAtualizacaoPlantioUC cadastrarAtualizacaoPlantioUC;

    @PostMapping
    public ResponseEntity<AtualizacaoPlantioDTO> cadastrarAtualizacaoPlantio(
            @RequestBody CadastrarAtualizacaoPlantioCommand command){
        AtualizacaoPlantioDTO atualizacaoPlantio = cadastrarAtualizacaoPlantioUC.executar(command);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(atualizacaoPlantio.id())
                .toUri();
        return ResponseEntity.created(location).body(atualizacaoPlantio);
    }
}
