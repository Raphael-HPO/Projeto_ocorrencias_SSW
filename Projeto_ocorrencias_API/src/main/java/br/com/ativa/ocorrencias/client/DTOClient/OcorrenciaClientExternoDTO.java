package br.com.ativa.ocorrencias.client.DTOClient;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OcorrenciaClientExternoDTO(
                @JsonProperty("accountId") Long idConta,
                @JsonProperty("channelId") Long idCanal,
                @JsonProperty("from") String remetente,
                @JsonProperty("contact") ContatoDestinatarioDTO destinatario,
                @JsonProperty("message") MensagemDTO mensagem,
                @JsonProperty("responseFlowId") Long idFlowResposta,
                @JsonProperty("session") SessaoDTO sessao) {
}
