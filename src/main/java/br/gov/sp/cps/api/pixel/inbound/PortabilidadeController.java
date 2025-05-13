package br.gov.sp.cps.api.pixel.inbound;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.gov.sp.cps.api.pixel.core.domain.dto.CriarChavePortabilidadeDTO;
import br.gov.sp.cps.api.pixel.core.domain.dto.command.PortabilidadeCriarChaveCommand;
import br.gov.sp.cps.api.pixel.core.usecase.CriarChavePortabilidadeUC;

@RestController
@RequestMapping("/api/portabilidade")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PortabilidadeController {
    private final CriarChavePortabilidadeUC criarChavePortabilidadeUC;

    @PostMapping()
    public ResponseEntity<CriarChavePortabilidadeDTO> criarChavePortabilidade(@RequestBody PortabilidadeCriarChaveCommand command) throws Exception{
        CriarChavePortabilidadeDTO chave = criarChavePortabilidadeUC.executar(command);
        return ResponseEntity.ok(chave);
    }
}
