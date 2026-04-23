package br.com.ativa.ocorrencias.client;

import org.springframework.context.annotation.Bean;

import br.com.ativa.ocorrencias.exceptions.MensagemWhatsappException;
import feign.codec.ErrorDecoder;

public class FeignConfig {
    @Bean
    public ErrorDecoder errorDecoder() {
        return (methodKey, response) -> {
            // Aqui você força o lançamento da SUA exceção
            return new MensagemWhatsappException(response.status(), "Erro ao enviar mensagem");
        };
    }
}
