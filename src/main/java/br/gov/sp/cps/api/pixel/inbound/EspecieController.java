package br.gov.sp.cps.api.pixel.inbound;

import br.gov.sp.cps.api.pixel.core.domain.dto.EspecieDTO;
import br.gov.sp.cps.api.pixel.core.domain.dto.command.CadastrarEspecieCommand;
import br.gov.sp.cps.api.pixel.core.usecase.CadastrarEspecieUC;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/especies")
@RequiredArgsConstructor
public class EspecieController {

    private final CadastrarEspecieUC cadastrarEspecieUC;

    @PostMapping
    public ResponseEntity<EspecieDTO> cadastrarEspecie(@RequestBody CadastrarEspecieCommand command) {
        EspecieDTO especie = cadastrarEspecieUC.executar(command);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(especie.id())
                .toUri();
        return ResponseEntity.created(location).body(especie);
    }
}
