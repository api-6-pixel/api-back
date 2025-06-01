package br.gov.sp.cps.api.pixel.inbound.rest;

import br.gov.sp.cps.api.pixel.core.domain.dto.AtualizacaoAceiteDTO;
import br.gov.sp.cps.api.pixel.core.domain.dto.TermoComItensDTO;
import br.gov.sp.cps.api.pixel.core.domain.dto.TermoDTO;
import br.gov.sp.cps.api.pixel.core.domain.dto.TermoItemAceiteDTO;
import br.gov.sp.cps.api.pixel.core.domain.dto.TermoItemAceiteDetalhadoDTO;
import br.gov.sp.cps.api.pixel.core.domain.dto.TermoItemCreateDTO;
import br.gov.sp.cps.api.pixel.core.domain.dto.TermoItemDTO;
import br.gov.sp.cps.api.pixel.core.domain.entity.Termo;
import br.gov.sp.cps.api.pixel.core.domain.entity.TermoItem;
import br.gov.sp.cps.api.pixel.core.domain.entity.TermoItemAceite;
import br.gov.sp.cps.api.pixel.core.domain.entity.TermoItemAceiteUsuarioHistorico;
import br.gov.sp.cps.api.pixel.core.domain.repository.TermoItemAceiteRepository;
import br.gov.sp.cps.api.pixel.core.domain.repository.TermoItemRepository;
import br.gov.sp.cps.api.pixel.core.domain.repository.TermoRepository;
import br.gov.sp.cps.api.pixel.core.service.TermoItemAceiteService;
import br.gov.sp.cps.api.pixel.core.service.TermoItemAceiteUsuarioHistoricoService;
import br.gov.sp.cps.api.pixel.core.usecase.CriarTermoItemUC;
import br.gov.sp.cps.api.pixel.core.usecase.CriarTermoUC;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/historico")
@CrossOrigin(origins = "*")
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
    @Autowired
    private CriarTermoItemUC criarTermoItemUC;
    @Autowired
    private CriarTermoUC criarTermoUC;

    @GetMapping("/termo")
    public ResponseEntity<Termo> getTermo() {
        Optional<Termo> termoOpt = historicoService.findLastTermo();

        return termoOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TermoItemAceiteUsuarioHistorico> createHistorico(
            @RequestBody TermoItemAceiteUsuarioHistorico historico) {
        return ResponseEntity.ok(historicoService.save(historico));
    }

    @PostMapping("/aceite")
    public ResponseEntity<?> registrarAceiteOuRecusa(@RequestBody TermoItemAceiteDTO request) {
        try {
            return ResponseEntity.ok(termoItemAceiteService.registrarAceite(
                    request.getUsuarioCodigo(),
                    request.getTermoItemCodigo(),
                    request.getAceito()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/aceitos")
    public ResponseEntity<List<TermoItemAceiteDTO>> getItensAceitos(@RequestParam Long usuarioCodigo) {
        try {
            List<TermoItemAceiteDTO> dtoList = termoItemAceiteService.getTermos(usuarioCodigo);
            return ResponseEntity.ok(dtoList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<TermoItemAceiteDetalhadoDTO>> listarAceitesPorUsuario(@PathVariable Long usuarioId) {
        List<TermoItemAceiteDetalhadoDTO> aceites = termoItemAceiteService.listarDetalhesAceitePorUsuario(usuarioId);
        return ResponseEntity.ok(aceites);
    }

    @PostMapping("/termo-item")
    public ResponseEntity<TermoItem> criarItemTermo(@RequestBody TermoItemCreateDTO dto) {
        TermoItem termoItem = criarTermoItemUC.criarTermo(dto);
        if (termoItem == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(201).body(termoItem);
    }

    @PostMapping("/inserir-termo")
    public ResponseEntity<?> inserirTermoFixo(@RequestBody TermoDTO dto) {
        try {
            Termo termo = criarTermoUC.executar(dto);
            return ResponseEntity.ok(termo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao inserir termo: " + e.getMessage());
        }
    }

    @GetMapping("/ativo")
    public ResponseEntity<TermoComItensDTO> getTermoAtivo(@RequestParam(required = false) Long usuarioCodigo) {
       Termo termoOpt = termoRepository.findTopByOrderByCodigoDesc();


        List<TermoItem> itens = termoItemRepository.findByTermoCodigo(termoOpt.getCodigo());

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
                    aceito);

            if (item.isObrigatorio()) {
                obrigatoriosAceitos.add(itemDTO);
            } else {
                opcionaisAceitos.add(itemDTO);
            }
        }

        TermoComItensDTO dto = new TermoComItensDTO();
        dto.setTermoId(termoOpt.getCodigo());
        dto.setDescricao(termoOpt.getDescricao());

        dto.setAceito(obrigatoriosAceitos.stream().allMatch(TermoComItensDTO.ItemDTO::isAceito));

        dto.setObrigatorios(obrigatoriosAceitos);
        dto.setOpcionais(opcionaisAceitos);

        return ResponseEntity.ok(dto);
    }

}

