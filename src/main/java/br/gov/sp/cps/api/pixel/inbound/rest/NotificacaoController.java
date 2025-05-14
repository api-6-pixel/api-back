package br.gov.sp.cps.api.pixel.inbound.rest;

import br.gov.sp.cps.api.pixel.core.usecase.NotificacaoUC;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/notificacoes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class NotificacaoController {

    private final NotificacaoUC notificacaoUC;

    @PostMapping
    public void notificar(
            @RequestParam List<String> dadosVazados,
            @RequestParam String medidas) {

        notificacaoUC.notificarVazamento(dadosVazados, medidas);
    }
}
