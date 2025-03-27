package br.gov.sp.cps.api.pixel.outbound.rest;

import br.gov.sp.cps.api.pixel.core.domain.dto.EnviarDadosAtualizacaoDTO;
import br.gov.sp.cps.api.pixel.core.domain.repository.EnviarDadosAtualizacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EnviarDadosAtualizacaoClient implements EnviarDadosAtualizacaoRepository {

    private final EnviarDadosAtualizacaoHttpClient enviarDadosAtualizacaoHttpClient;
    @Override
    public void enviar(EnviarDadosAtualizacaoDTO enviarDadosAtualizacaoDTO) {
        enviarDadosAtualizacaoHttpClient.enviar(enviarDadosAtualizacaoDTO);
    }
}
