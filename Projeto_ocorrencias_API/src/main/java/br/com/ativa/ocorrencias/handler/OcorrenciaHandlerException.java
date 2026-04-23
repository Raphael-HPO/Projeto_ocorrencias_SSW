package br.com.ativa.ocorrencias.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.ativa.ocorrencias.exceptions.MensagemWhatsappException;
import br.com.ativa.ocorrencias.exceptions.OcorrenciaNaoEncontradaException;
import br.com.ativa.ocorrencias.exceptions.StatusInvalidoException;
import br.com.ativa.ocorrencias.handler.DTOHandler.ExceptionDTO;

@RestControllerAdvice
public class OcorrenciaHandlerException {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDTO> handleGeneralException(Exception e) {
        // Logamos o erro real no console para o desenvolvedor ver
        e.printStackTrace();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ExceptionDTO(HttpStatus.INTERNAL_SERVER_ERROR,
                        e.getMessage()));
    }

    @ExceptionHandler(OcorrenciaNaoEncontradaException.class)
    public ResponseEntity<ExceptionDTO> OcorrenciaNaoEncontradaHandler(OcorrenciaNaoEncontradaException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionDTO(HttpStatus.NOT_FOUND, e.getMessage()));
    }

    @ExceptionHandler(MensagemWhatsappException.class)
    public ResponseEntity<ExceptionDTO> mensagemWhatsappHandler(MensagemWhatsappException e) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                .body(new ExceptionDTO(HttpStatus.BAD_GATEWAY, e.getMessage()));
    }

    @ExceptionHandler(StatusInvalidoException.class)
    public ResponseEntity<ExceptionDTO> statusInvalidoHandler(StatusInvalidoException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionDTO(HttpStatus.BAD_REQUEST, e.getMessage()));
    }
}
