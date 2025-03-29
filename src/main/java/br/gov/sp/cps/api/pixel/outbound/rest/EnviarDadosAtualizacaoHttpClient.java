package br.gov.sp.cps.api.pixel.outbound.rest;

import br.gov.sp.cps.api.pixel.core.domain.dto.EnviarDadosAtualizacaoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "api-ai", url = "${feign.url}")
public interface EnviarDadosAtualizacaoHttpClient {
    @PostMapping("/incluir-atualizacao")
    void enviar(@RequestBody EnviarDadosAtualizacaoDTO dadosAtualizacaoDTO);
}
