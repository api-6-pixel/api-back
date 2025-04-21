package br.gov.sp.cps.api.pixel.inbound;

import br.gov.sp.cps.api.pixel.core.domain.dto.TermoComItensDTO;
import br.gov.sp.cps.api.pixel.core.domain.dto.TermoItemAceiteDTO;
import br.gov.sp.cps.api.pixel.core.domain.entity.Termo;
import br.gov.sp.cps.api.pixel.core.domain.entity.TermoItem;
import br.gov.sp.cps.api.pixel.core.domain.entity.TermoItemAceite;
import br.gov.sp.cps.api.pixel.core.domain.entity.TermoItemAceiteUsuarioHistorico;
import br.gov.sp.cps.api.pixel.core.domain.repository.TermoItemAceiteRepository;
import br.gov.sp.cps.api.pixel.core.domain.repository.TermoItemRepository;
import br.gov.sp.cps.api.pixel.core.domain.repository.TermoRepository;
import br.gov.sp.cps.api.pixel.core.service.TermoItemAceiteService;
import br.gov.sp.cps.api.pixel.core.service.TermoItemAceiteUsuarioHistoricoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/historico")
public class TermoItemAceiteUsuarioHistoricoController {

    @Autowired
    private TermoRepository termoRepository;

    @Autowired
    private TermoItemRepository termoItemRepository;

    @Autowired
    private TermoItemAceiteRepository termoItemAceiteRepository;

    @Autowired
    private TermoItemAceiteUsuarioHistoricoService historicoService;

    @Autowired
    private TermoItemAceiteService termoItemAceiteService;

    @GetMapping("/{id}")
    public ResponseEntity<TermoItemAceiteUsuarioHistorico> getHistoricoById(@PathVariable Long id) {
        Optional<TermoItemAceiteUsuarioHistorico> historico = historicoService.findById(id);
        return historico.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TermoItemAceiteUsuarioHistorico> createHistorico(
            @RequestBody TermoItemAceiteUsuarioHistorico historico) {
        return ResponseEntity.ok(historicoService.save(historico));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHistorico(@PathVariable Long id) {
        historicoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/aceite")
    public ResponseEntity<?> registrarAceiteOuRecusa(@RequestBody TermoItemAceiteDTO request) {
        try {
            return ResponseEntity.ok(termoItemAceiteService.registrarAceite(
                    request.getUsuarioCodigo(),
                    request.getTermoItemCodigo(),
                    request.isAceito()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    

    @GetMapping("/ativo")
    public ResponseEntity<TermoComItensDTO> getTermoAtivo(@RequestParam(required = false) Long usuarioCodigo) {
    Termo termo = termoRepository.findTopByOrderByCodigoDesc();

    List<TermoItem> itens = termoItemRepository.findByTermoCodigo(termo.getCodigo());

    List<TermoComItensDTO.ItemDTO> obrigatoriosAceitos = new ArrayList<>();
    List<TermoComItensDTO.ItemDTO> opcionaisAceitos = new ArrayList<>();

    for (TermoItem item : itens) {
        boolean aceito = false;

        if (usuarioCodigo != null) {
            Optional<TermoItemAceite> termoAceite = termoItemAceiteRepository
                    .findTopByTermoAceiteUsuarioHistoricoUsuarioIdAndTermoItemCodigoOrderByCodigoDesc(
                            usuarioCodigo, item.getCodigo());

            aceito = termoAceite.map(TermoItemAceite::isAceito).orElse(false);
        }

        TermoComItensDTO.ItemDTO itemDTO = new TermoComItensDTO.ItemDTO(
                item.getCodigo(),
                item.getDescricao(),
                aceito
        );

        if (item.isObrigatorio()) {
            obrigatoriosAceitos.add(itemDTO);
        } else {
            opcionaisAceitos.add(itemDTO);
        }
    }

    TermoComItensDTO dto = new TermoComItensDTO();
    dto.setTermoId(termo.getCodigo());
    dto.setDescricao(termo.getDescricao());

    dto.setAceito(obrigatoriosAceitos.stream().allMatch(TermoComItensDTO.ItemDTO::isAceito));

    dto.setObrigatorios(obrigatoriosAceitos);
    dto.setOpcionais(opcionaisAceitos);

    return ResponseEntity.ok(dto);
}

}
