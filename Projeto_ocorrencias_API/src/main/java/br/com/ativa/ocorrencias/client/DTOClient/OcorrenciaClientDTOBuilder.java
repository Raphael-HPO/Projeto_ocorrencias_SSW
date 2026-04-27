package br.com.ativa.ocorrencias.client.DTOClient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class OcorrenciaClientDTOBuilder {
    @Value("${id.conta}")
    private Long idConta;
    @Value("${id.canal}")
    private Long idCanal;
    @Value("${id.flow.resposta}")
    private Long idFlowResposta;

    public OcorrenciaClientExternoDTO build(
            String remetente,
            ContatoDestinatarioDTO destinatario,
            MensagemDTO mensagem,
            SessaoDTO sessao) {
        return new OcorrenciaClientExternoDTO(
                idConta,
                idCanal,
                remetente,
                destinatario,
                mensagem,
                idFlowResposta,
                sessao);
    }

}
