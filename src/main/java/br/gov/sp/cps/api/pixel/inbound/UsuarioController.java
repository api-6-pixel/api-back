package br.gov.sp.cps.api.pixel.inbound;

import br.gov.sp.cps.api.pixel.core.domain.dto.PlantacaoDTO;
import br.gov.sp.cps.api.pixel.core.domain.dto.UsuarioDTO;
import br.gov.sp.cps.api.pixel.core.domain.dto.command.AlterarUsuarioCommand;
import br.gov.sp.cps.api.pixel.core.domain.dto.command.CadastrarPlantacaoCommand;
import br.gov.sp.cps.api.pixel.core.domain.dto.command.CadastrarUsuarioCommand;
import br.gov.sp.cps.api.pixel.core.domain.dto.command.DeletarUsuarioCommand;
import br.gov.sp.cps.api.pixel.core.domain.entity.Usuario;
import br.gov.sp.cps.api.pixel.core.usecase.AlterarUsuarioUC;
import br.gov.sp.cps.api.pixel.core.usecase.CadastrarUsuarioUC;
import br.gov.sp.cps.api.pixel.core.usecase.CarregarUsuarioUC;
import br.gov.sp.cps.api.pixel.core.usecase.DeletarUsuarioUC;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final CadastrarUsuarioUC cadastrarUsuarioUC;
    private final DeletarUsuarioUC deletarUsuarioUC;
    private final CarregarUsuarioUC carregarUsuarioUC;
    private final AlterarUsuarioUC alterarUsuarioUC;

    @PostMapping
    public ResponseEntity<Usuario> cadastrarUsuario(@RequestBody CadastrarUsuarioCommand command) throws Exception {
        Usuario usuario = cadastrarUsuarioUC.executar(command);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(usuario.getId())
                .toUri();
        return ResponseEntity.created(location).body(usuario);
    }

    @GetMapping("/{idUsuario}")
    public ResponseEntity<UsuarioDTO>  obterUsuario(@PathVariable Long idUsuario) throws Exception {
        UsuarioDTO usuario = carregarUsuarioUC.executar(idUsuario);
        return ResponseEntity.ok(usuario);
    }

    @PutMapping("/{idUsuario}")
    public ResponseEntity<Usuario> alterarUsuario(@PathVariable Long idUsuario, @RequestBody AlterarUsuarioCommand command) throws Exception {
        command.setId(idUsuario); // Garante que o ID da URL prevaleça
        Usuario usuario = alterarUsuarioUC.executar(command);
        return ResponseEntity.ok(usuario);
    }

    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<Usuario> deletarUsuario(@PathVariable Long idUsuario) throws Exception {
        deletarUsuarioUC.executar(new DeletarUsuarioCommand(idUsuario));
        return ResponseEntity.ok().build();
    }
}
