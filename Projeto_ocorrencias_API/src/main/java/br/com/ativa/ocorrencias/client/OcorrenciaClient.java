package br.com.ativa.ocorrencias.client;

import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import br.com.ativa.ocorrencias.client.DTOClient.OcorrenciaClientExternoDTO;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@FeignClient(name = "ocorrenciaClient", url = "https://api.sacflow.io/api", configuration = FeignConfig.class)
public interface OcorrenciaClient {
    @PostMapping(value = "/send-message", consumes = "application/json")
    public Optional<String> envioMensagemWhatsApp(
            @RequestHeader("Authorization") String token,
            @RequestBody OcorrenciaClientExternoDTO menssagem);
}
