package br.gov.sp.cps.api.pixel.core.service;

import br.gov.sp.cps.api.pixel.core.domain.dto.AtualizacaoAceiteDTO;
import br.gov.sp.cps.api.pixel.core.domain.dto.TermoItemAceiteDTO;
import br.gov.sp.cps.api.pixel.core.domain.dto.TermoItemAceiteDetalhadoDTO;
import br.gov.sp.cps.api.pixel.core.domain.entity.Termo;
import br.gov.sp.cps.api.pixel.core.domain.entity.TermoItem;
import br.gov.sp.cps.api.pixel.core.domain.entity.TermoItemAceite;
import br.gov.sp.cps.api.pixel.core.domain.entity.TermoItemAceiteUsuarioHistorico;
import br.gov.sp.cps.api.pixel.core.domain.entity.Usuario;
import br.gov.sp.cps.api.pixel.core.domain.repository.TermoItemAceiteRepository;
import br.gov.sp.cps.api.pixel.core.domain.repository.TermoItemAceiteUsuarioHistoricoRepository;
import br.gov.sp.cps.api.pixel.core.domain.repository.TermoItemRepository;
import br.gov.sp.cps.api.pixel.core.domain.repository.TermoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TermoItemAceiteService {

    @Autowired
    private TermoItemAceiteRepository aceiteRepository;

    @Autowired
    private TermoItemRepository termoItemRepository;

    @Autowired
    private TermoItemAceiteUsuarioHistoricoRepository historicoRepository;

    @Autowired
    private TermoRepository termoRepository;

    @Transactional
    public int atualizarAceite(Long usuarioId, Long termoId, Boolean novoValor) {
        return aceiteRepository.atualizarAceitoPorUsuario(usuarioId, termoId, novoValor);
    }

    public List<TermoItemAceiteDTO> getTermos(Long usuarioCodigo) {
        List<TermoItemAceiteDTO> dtoList = aceiteRepository.findAceitesByUsuarioId(usuarioCodigo);
        return dtoList;
    }

    public List<TermoItemAceiteDetalhadoDTO> listarDetalhesAceitePorUsuario(Long usuarioId) {
        return aceiteRepository.buscarDetalhesPorUsuario(usuarioId);
    }

    public TermoItemAceite registrarAceite(Long usuarioCodigo, Long termoItemCodigo, boolean aceito) {

        TermoItem termoItem = termoItemRepository.findById(termoItemCodigo)
                .orElseThrow(() -> new RuntimeException("TermoItem não encontrado"));

        Termo termo = termoRepository.findTopByOrderByCodigoDesc();

        TermoItemAceiteUsuarioHistorico historico = new TermoItemAceiteUsuarioHistorico();
        historico.setUsuario(new Usuario(usuarioCodigo));
        historico.setTermo(termo); 
        historico.setDataAceite(LocalDateTime.now());
        historico.setDataAlteracao(LocalDateTime.now());

        TermoItemAceiteUsuarioHistorico historicoSalvo = historicoRepository.save(historico);

        TermoItemAceite termoItemAceite = new TermoItemAceite();
        termoItemAceite.setTermoAceiteUsuarioHistorico(historicoSalvo);
        termoItemAceite.setTermoItem(termoItem);
        termoItemAceite.setAceito(aceito);

        return aceiteRepository.save(termoItemAceite);
    }

}