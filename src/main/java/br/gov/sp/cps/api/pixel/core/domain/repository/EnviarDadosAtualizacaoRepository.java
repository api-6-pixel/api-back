package br.gov.sp.cps.api.pixel.core.domain.repository;

import br.gov.sp.cps.api.pixel.core.domain.dto.EnviarDadosAtualizacaoDTO;

public interface EnviarDadosAtualizacaoRepository {
    void enviar(EnviarDadosAtualizacaoDTO enviarDadosAtualizacaoDTO);
}
