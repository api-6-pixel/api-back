package br.gov.sp.cps.api.pixel.inbound.rest;

import br.gov.sp.cps.api.pixel.core.domain.dto.AutenticacaoDTO;
import br.gov.sp.cps.api.pixel.core.domain.dto.LoginDTO;
import br.gov.sp.cps.api.pixel.core.usecase.RealizarLoginUC;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AutenticacaoController {

    private final RealizarLoginUC realizarLoginUC;

    @PostMapping("/login")
    public ResponseEntity<LoginDTO> login(@RequestBody AutenticacaoDTO data) {
        LoginDTO response = realizarLoginUC.realizarLogin(data);
        return ResponseEntity.ok(response);
    }
}