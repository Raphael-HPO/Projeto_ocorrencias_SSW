package br.com.ativa.ocorrencias.handler.DTOHandler;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ExceptionDTO {
    HttpStatus statusCode;
    String statusCodeMessage;
}
