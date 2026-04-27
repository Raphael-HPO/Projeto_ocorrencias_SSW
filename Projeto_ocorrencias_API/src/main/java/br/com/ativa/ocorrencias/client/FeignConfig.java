package br.com.ativa.ocorrencias.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.ativa.ocorrencias.exceptions.MensagemWhatsappException;
import feign.codec.ErrorDecoder;

@Configuration
public class FeignConfig {
    @Bean
    public ErrorDecoder errorDecoder() {
        return (methodKey, response) -> {
            try {
                String body = new String(response.body().asInputStream().readAllBytes());
                System.out.println("Status: " + response.status());
                System.out.println("Body da resposta: " + body);
            } catch (Exception e) {
                System.out.println("Erro ao ler body: " + e.getMessage());
            }
            return new MensagemWhatsappException(response.status(), "Erro ao enviar mensagem");
        };
    }
}
