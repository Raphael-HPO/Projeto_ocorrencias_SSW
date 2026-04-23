package br.com.ativa.ocorrencias.exceptions;

import feign.FeignException;

public class MensagemWhatsappException extends FeignException {

    public MensagemWhatsappException(int status, String mensagem) {
        super(status, mensagem);
    }
}
