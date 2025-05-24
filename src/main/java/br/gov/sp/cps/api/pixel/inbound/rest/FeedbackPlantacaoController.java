package br.gov.sp.cps.api.pixel.inbound.rest;

import br.gov.sp.cps.api.pixel.core.domain.dto.FeedbackPlantacaoDTO;
import br.gov.sp.cps.api.pixel.core.domain.dto.StatusDTO;
import br.gov.sp.cps.api.pixel.core.usecase.CarregarStatusPlantacaoUC;
import br.gov.sp.cps.api.pixel.core.usecase.ObterFeedbackPlantacaoUC;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedback/plantacao")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class FeedbackPlantacaoController {

    private final ObterFeedbackPlantacaoUC obterFeedbackPlantacaoUC;
    private final CarregarStatusPlantacaoUC carregarStatusPlantacaoUC;

    @GetMapping("/melhoria/{idAtualizacao}")
    public ResponseEntity<List<FeedbackPlantacaoDTO>> obterFeedback(@PathVariable Long idAtualizacao) {
        List<FeedbackPlantacaoDTO> feedbackPlantacao = obterFeedbackPlantacaoUC.executar(idAtualizacao);
        return ResponseEntity.ok(feedbackPlantacao);
    }

    @GetMapping("/status")
    public ResponseEntity<List<StatusDTO>> obterStatus() {
        List<StatusDTO> statusDTOList = carregarStatusPlantacaoUC.executar();
        return ResponseEntity.ok(statusDTOList);
    }
}

