package br.com.ativa.ocorrencias.client.DTOClient;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ContextoDTO(
        @JsonProperty("key") String key) {

}
