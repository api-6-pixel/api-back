package br.gov.sp.cps.api.pixel.core.service;

import br.gov.sp.cps.api.pixel.core.domain.entity.TermoItemAceiteUsuarioHistorico;
import br.gov.sp.cps.api.pixel.core.domain.repository.TermoItemAceiteUsuarioHistoricoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TermoItemAceiteUsuarioHistoricoService {

    @Autowired
    private TermoItemAceiteUsuarioHistoricoRepository historicoRepository;


    public Optional<TermoItemAceiteUsuarioHistorico> findLastAceiteByUsuario(Long usuarioCodigo) {
        return historicoRepository.findTopByUsuario_IdOrderByDataAceiteDesc(usuarioCodigo);
    }

    public Optional<TermoItemAceiteUsuarioHistorico> findById(Long id) {
        return historicoRepository.findById(id);
    }

    public TermoItemAceiteUsuarioHistorico save(TermoItemAceiteUsuarioHistorico historico) {
        return historicoRepository.save(historico);
    }

    public void delete(Long id) {
        historicoRepository.deleteById(id);
    }
}
