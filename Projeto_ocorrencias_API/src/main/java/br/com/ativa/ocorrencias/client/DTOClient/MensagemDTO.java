package br.com.ativa.ocorrencias.client.DTOClient;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MensagemDTO(
                @JsonProperty("type") String tipo,
                @JsonProperty("name") String nome,
                @JsonProperty("variables") List<String> dados,
                @JsonProperty("metadata") MetadataDTO metadata) {

}
