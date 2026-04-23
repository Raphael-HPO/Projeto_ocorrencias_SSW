package br.com.ativa.ocorrencias.exceptions;

public class OcorrenciaNaoEncontradaException extends RuntimeException {
    public OcorrenciaNaoEncontradaException() {
        super();
    }

    public OcorrenciaNaoEncontradaException(String message) {
        super(message);
    }
}
