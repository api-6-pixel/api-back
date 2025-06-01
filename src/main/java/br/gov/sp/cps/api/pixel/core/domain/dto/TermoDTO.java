package br.gov.sp.cps.api.pixel.core.domain.dto;

public record TermoDTO(
    Long termoCodigo,
    String titulo,
    String descricao,
    String versao
    
) {}
