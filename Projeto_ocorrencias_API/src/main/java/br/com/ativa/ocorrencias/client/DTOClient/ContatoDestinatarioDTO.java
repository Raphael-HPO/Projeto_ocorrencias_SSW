package br.com.ativa.ocorrencias.client.DTOClient;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ContatoDestinatarioDTO(
        @JsonProperty("name") String nome,
        @JsonProperty("phone") String telefone) {
}
