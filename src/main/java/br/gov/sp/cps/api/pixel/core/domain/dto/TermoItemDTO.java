package br.gov.sp.cps.api.pixel.core.domain.dto;

public record TermoItemDTO(
    Long termoCodigo,
    String titulo,
    String descricao,
    java.time.LocalDateTime dataCriacao,
    String versao,
    
    Long termoItemCodigo,
    String termoItemDescricao,
    Boolean termoItemObrigatorio,
    
    Long usuarioCodigo,
    Boolean itemAceito
) {}
