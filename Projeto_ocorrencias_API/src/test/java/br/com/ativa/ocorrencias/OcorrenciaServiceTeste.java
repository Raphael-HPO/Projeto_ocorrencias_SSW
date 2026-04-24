package br.com.ativa.ocorrencias;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.ativa.ocorrencias.exceptions.OcorrenciaNaoEncontradaException;
import br.com.ativa.ocorrencias.repository.OcorrenciaRepository;
import br.com.ativa.ocorrencias.service.OcorrenciaService;

@ExtendWith(MockitoExtension.class)
public class OcorrenciaServiceTeste {

    @Spy
    OcorrenciaRepository repository;

    @InjectMocks
    OcorrenciaService service;

    @Test
    public void testeDeRetornoThrowVerificarOcorrencia() {
        assertThrows(OcorrenciaNaoEncontradaException.class, () -> service.verificarOcorrencia(""));
    }

    @Test
    
}
