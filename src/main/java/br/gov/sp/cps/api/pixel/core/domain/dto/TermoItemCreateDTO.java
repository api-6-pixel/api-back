package br.gov.sp.cps.api.pixel.core.domain.dto;


public record TermoItemCreateDTO(
    Long codigo,         
    Long termoCodigo,    
    String descricao,    
    Boolean obrigatorio  
) {}
