package br.gov.sp.cps.api.pixel.core.usecase;

import java.util.Optional;

import org.springframework.stereotype.Service;

import br.gov.sp.cps.api.pixel.core.domain.dto.TermoItemCreateDTO;
import br.gov.sp.cps.api.pixel.core.domain.entity.Termo;
import br.gov.sp.cps.api.pixel.core.domain.entity.TermoItem;
import br.gov.sp.cps.api.pixel.core.domain.repository.TermoItemRepository;
import br.gov.sp.cps.api.pixel.core.domain.repository.TermoRepository;

@Service
public class CriarTermoItemUC {

    private final TermoItemRepository termoItemRepository;
    private final TermoRepository termoRepository;

    public CriarTermoItemUC(TermoItemRepository termoItemRepository, TermoRepository termoRepository) {
        this.termoItemRepository = termoItemRepository;
        this.termoRepository = termoRepository;
    }

    public TermoItem criarTermo(TermoItemCreateDTO dto) {
        Optional<Termo> termoOptional = termoRepository.findById(dto.termoCodigo());

        if (termoOptional.isEmpty()) {
            return null;
        }

        TermoItem novoItem = new TermoItem();
        novoItem.setTermo(termoOptional.get());
        novoItem.setDescricao(dto.descricao());
        novoItem.setObrigatorio(dto.obrigatorio());

        return termoItemRepository.save(novoItem);
    }
}
