package br.gov.sp.cps.api.pixel.inbound;

import br.gov.sp.cps.api.pixel.core.domain.dto.AutenticacaoDTO;
import br.gov.sp.cps.api.pixel.core.domain.dto.LoginDTO;
import br.gov.sp.cps.api.pixel.core.usecase.RealizarLoginUC;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AutenticacaoController {

    private final RealizarLoginUC realizarLoginUC;

    @PostMapping("/login")
    public ResponseEntity<LoginDTO> login(@RequestBody AutenticacaoDTO data) {
        LoginDTO response = realizarLoginUC.realizarLogin(data);
        return ResponseEntity.ok(response);
    }
}