package br.com.ativa.ocorrencias.client.DTOClient;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MetadataDTO(
        @JsonProperty("key") String chave) {

}
