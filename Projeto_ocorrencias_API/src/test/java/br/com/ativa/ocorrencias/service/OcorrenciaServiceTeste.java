package br.com.ativa.ocorrencias.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.ativa.ocorrencias.DTO.CreateOcorrenciaDTO;
import br.com.ativa.ocorrencias.DTO.Usuario;
import br.com.ativa.ocorrencias.exceptions.OcorrenciaNaoEncontradaException;
import br.com.ativa.ocorrencias.model.entity.Ocorrencia;
import br.com.ativa.ocorrencias.model.enums.Retorno;
import br.com.ativa.ocorrencias.repository.OcorrenciaRepository;

@ExtendWith(MockitoExtension.class)
public class OcorrenciaServiceTeste {

    @Mock
    OcorrenciaRepository repository;

    @Spy
    @InjectMocks
    OcorrenciaService service;

    static String formatKey = "raphaeloMTZ123123452404261100";
    static Ocorrencia ocorrenciaTeste = new Ocorrencia(formatKey, Retorno.Aguardando);
    static Usuario usuarioTeste = new Usuario("raphaelo", "MTZ");
    static CreateOcorrenciaDTO ocorrenciaTesteDTO = new CreateOcorrenciaDTO(
            "123123",
            "123",
            "descrição da Ocorrencia",
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyy")),
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("HHmm")),
            formatKey,
            usuarioTeste);

    @BeforeEach
    void dadosFixos() {
        formatKey = "raphaeloMTZ123123452404261100";
        ocorrenciaTeste = new Ocorrencia(formatKey, Retorno.Aguardando);
        usuarioTeste = new Usuario("raphaelo", "MTZ");
        ocorrenciaTesteDTO = new CreateOcorrenciaDTO(
                "123123",
                "123",
                "descrição da Ocorrencia",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyy")),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("HHmm")),
                formatKey,
                usuarioTeste);
    }

    @Test
    void deveVerificarAExistenciaDeUmaOcorrencia() {
        when(repository.findById(formatKey)).thenReturn(Optional.of(ocorrenciaTeste));

        assertNotNull(service.verificarOcorrencia(formatKey));
    }

    @Test
    void deveSalvarUmaNovaOcorrencia() {
        when(repository.save(any(Ocorrencia.class))).thenReturn(ocorrenciaTeste);

        doNothing().when(service).enviarMensagemWhatsApp(any(CreateOcorrenciaDTO.class));

        assertNotNull(service.criarOcorrencia(ocorrenciaTesteDTO));
    }

    @Test
    void deveIncluirEnumSimAOcorrencia() {
        ocorrenciaTeste.setRetorno(Retorno.Sim);

        assertEquals(ocorrenciaTeste.getRetorno(), Retorno.Sim);
    }

    @Test
    void deveIncluirEnumNaoAOcorrencia() {
        ocorrenciaTeste.setRetorno(Retorno.Nao);

        assertEquals(ocorrenciaTeste.getRetorno(), Retorno.Nao);
    }

    @Test
    void deveLancarOcorrenciaNaoEncontradaException() {
        when(repository.findById(formatKey)).thenReturn(Optional.empty());
        assertThrows(OcorrenciaNaoEncontradaException.class, () -> service.verificarOcorrencia(formatKey));
    }

}
