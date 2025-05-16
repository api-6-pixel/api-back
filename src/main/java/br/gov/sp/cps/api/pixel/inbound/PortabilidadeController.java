package br.gov.sp.cps.api.pixel.inbound;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.gov.sp.cps.api.pixel.core.domain.dto.CriarChavePortabilidadeDTO;
import br.gov.sp.cps.api.pixel.core.domain.dto.PortabilidadeDTO;
import br.gov.sp.cps.api.pixel.core.domain.dto.UsuarioDTO;
import br.gov.sp.cps.api.pixel.core.domain.dto.command.ObterUsuarioIDCommand;
import br.gov.sp.cps.api.pixel.core.domain.dto.command.PortabilidadeCriarChaveCommand;
import br.gov.sp.cps.api.pixel.core.usecase.CriarChavePortabilidadeUC;
import br.gov.sp.cps.api.pixel.core.usecase.ObterUsuarioUC;

@RestController
@RequestMapping("/api/portabilidade")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PortabilidadeController {
    private final CriarChavePortabilidadeUC criarChavePortabilidadeUC;
    private final ObterUsuarioUC obterUsuarioUC;

    @PostMapping()
    public ResponseEntity<CriarChavePortabilidadeDTO> criarChavePortabilidade(@RequestBody PortabilidadeCriarChaveCommand command) throws Exception{
        CriarChavePortabilidadeDTO chave = criarChavePortabilidadeUC.executar(command);
        return ResponseEntity.ok(chave);
    }

    @GetMapping
    public ResponseEntity<PortabilidadeDTO> obterUsuarioID (@RequestBody ObterUsuarioIDCommand command) throws Exception{
        PortabilidadeDTO chave = obterUsuarioUC.executar(command);
        return ResponseEntity.ok(chave);
    }
}
