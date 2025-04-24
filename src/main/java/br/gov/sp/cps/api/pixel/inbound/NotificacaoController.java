package br.gov.sp.cps.api.pixel.inbound;

import br.gov.sp.cps.api.pixel.core.usecase.NotificacaoUC;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/notificacoes")
@RequiredArgsConstructor
public class NotificacaoController {

    private final NotificacaoUC notificacaoUC;

    @PostMapping
    public void notificar(
            @RequestParam List<String> dadosVazados,
            @RequestParam String medidas) {

        notificacaoUC.notificarVazamento(dadosVazados, medidas);
    }
}
