package br.gov.sp.cps.api.pixel.inbound;

import br.gov.sp.cps.api.pixel.core.domain.dto.DadosRelatorioDTO;
import br.gov.sp.cps.api.pixel.core.domain.dto.RelatorioDTO;
import br.gov.sp.cps.api.pixel.core.usecase.GerarRelatorioUC;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/relatorio")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class RelatorioController {

    private final GerarRelatorioUC gerarRelatorioUC;

    @GetMapping("/{idUsuario}/plantacao/{idPlantacao}")
    public ResponseEntity<InputStreamResource> downloadRelatorioFazendaEspecifica(
            @PathVariable Long idUsuario,
            @PathVariable Long idPlantacao) {
        try {
            RelatorioDTO relatorio = gerarRelatorioUC.executar(idUsuario, idPlantacao);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + relatorio.nomeArquivo())
                    .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                    .body(relatorio.arquivo());
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
