package br.gov.sp.cps.api.pixel.core.usecase;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import br.gov.sp.cps.api.pixel.core.domain.dto.TermoDTO;
import br.gov.sp.cps.api.pixel.core.domain.entity.Termo;
import br.gov.sp.cps.api.pixel.core.domain.repository.TermoRepository;

@Service
public class CriarTermoUC {

    private final TermoRepository termoRepository;

    public CriarTermoUC(TermoRepository termoRepository) {
        this.termoRepository = termoRepository;
    }

    public Termo executar(TermoDTO dto) {
        Termo termo = new Termo();
        termo.setCodigo(dto.termoCodigo());
        termo.setTitulo(dto.titulo());
        termo.setDescricao(dto.descricao());
        termo.setDataCriacao(LocalDateTime.now());
        termo.setVersao("1.0");

        return termoRepository.save(termo);
    }
}
