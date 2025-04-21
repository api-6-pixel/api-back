package br.gov.sp.cps.api.pixel.core.service;

import br.gov.sp.cps.api.pixel.core.domain.entity.Termo;
import br.gov.sp.cps.api.pixel.core.domain.entity.TermoItem;
import br.gov.sp.cps.api.pixel.core.domain.entity.TermoItemAceite;
import br.gov.sp.cps.api.pixel.core.domain.entity.TermoItemAceiteUsuarioHistorico;
import br.gov.sp.cps.api.pixel.core.domain.entity.Usuario;
import br.gov.sp.cps.api.pixel.core.domain.repository.TermoItemAceiteRepository;
import br.gov.sp.cps.api.pixel.core.domain.repository.TermoItemAceiteUsuarioHistoricoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class TermoItemAceiteService {

    @Autowired
    private TermoItemAceiteRepository aceiteRepository;

    @Autowired
    private TermoItemAceiteUsuarioHistoricoRepository historicoRepository;

    public TermoItemAceite registrarAceite(Long usuarioCodigo, Long termoItemCodigo, boolean aceito) {
      
        TermoItemAceiteUsuarioHistorico historico = new TermoItemAceiteUsuarioHistorico();
        
        Usuario usuario = new Usuario();
        usuario.setId(usuarioCodigo);
        
        Termo termo = new Termo();
        termo.setCodigo(termoItemCodigo); 
    
        historico.setUsuario(usuario);
        historico.setTermo(termo);
        historico.setDataAceite(LocalDateTime.now());
        historico.setDataAlteracao(LocalDateTime.now());
    
        TermoItemAceiteUsuarioHistorico historicoSalvo = historicoRepository.save(historico);
    
        TermoItem termoItem = new TermoItem();
        termoItem.setCodigo(termoItemCodigo);
    
        TermoItemAceite termoItemAceite = new TermoItemAceite();
        termoItemAceite.setTermoAceiteUsuarioHistorico(historicoSalvo); 
        termoItemAceite.setTermoItem(termoItem);
        termoItemAceite.setAceito(aceito);
    
        return aceiteRepository.save(termoItemAceite);
    }
}    