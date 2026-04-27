package br.com.ativa.ocorrencias.client.DTOClient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter
public class KeyApiWhatsapp {
    @Value("${token.whatsapp}")
    private String token;
}
