package br.gov.sp.cps.api.pixel.inbound;

import br.gov.sp.cps.api.pixel.core.domain.dto.PortabilidadeLoginDTO;
import br.gov.sp.cps.api.pixel.core.domain.dto.command.AutorizarPortabilidadeCommand;
import br.gov.sp.cps.api.pixel.core.domain.dto.command.PortabilidadeLoginCommand;
import br.gov.sp.cps.api.pixel.core.usecase.AutorizarPortabilidadeUC;
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
import br.gov.sp.cps.api.pixel.core.usecase.PortabilidadeLoginUC;
import br.gov.sp.cps.api.pixel.core.usecase.ObterUsuarioUC;

@RestController
@RequestMapping("/api/portabilidade")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PortabilidadeController {
    private final CriarChavePortabilidadeUC criarChavePortabilidadeUC;
    private final ObterUsuarioUC obterUsuarioUC;
    private final AutorizarPortabilidadeUC autorizarUsuarioUC;
    private final PortabilidadeLoginUC loginUC;

    @PostMapping("/auth")
    public ResponseEntity<PortabilidadeLoginDTO> login(@RequestBody PortabilidadeLoginCommand command) throws Exception{
        var chave = loginUC.executar(command);
        return ResponseEntity.ok(chave);
    }

    @PostMapping()
    public ResponseEntity<CriarChavePortabilidadeDTO> criarChavePortabilidade(@RequestBody PortabilidadeCriarChaveCommand command) throws Exception{
        CriarChavePortabilidadeDTO chave = criarChavePortabilidadeUC.executar(command);
        return ResponseEntity.ok(chave);
    }

    @PostMapping("/getuser")
    public ResponseEntity<PortabilidadeDTO> obterUsuarioID (@RequestBody ObterUsuarioIDCommand command) throws Exception{
        PortabilidadeDTO chave = obterUsuarioUC.executar(command);
        return ResponseEntity.ok(chave);
    }

    @PutMapping("/authorize")
    public ResponseEntity autorizarPortabilidade(@RequestBody AutorizarPortabilidadeCommand command) throws Exception{
        autorizarUsuarioUC.executar(command);
        return ResponseEntity.ok("Autorizado com Sucesso");
    }
}
