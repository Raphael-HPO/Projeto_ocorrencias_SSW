package br.com.ativa.ocorrencias.exceptions;

public class StatusInvalidoException extends IllegalArgumentException {
    public StatusInvalidoException() {
        super();
    }

    public StatusInvalidoException(String message) {
        super(message);
    }
}
