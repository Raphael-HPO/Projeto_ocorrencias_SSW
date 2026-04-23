package br.com.ativa.ocorrencias.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ativa.ocorrencias.DTO.CreateOcorrenciaDTO;
import br.com.ativa.ocorrencias.model.entity.Ocorrencia;
import br.com.ativa.ocorrencias.service.OcorrenciaService;

@CrossOrigin("*") // Permite acesso de outros navegadores, evitando erros de CORS
@RestController
@RequestMapping("/ocorrencia")
// TODO: implementar um ExceptionHandler para tratar as exceções de
// forma personalizada e globalmente
public class OcorrenciaController {
    OcorrenciaService service;

    private OcorrenciaController(OcorrenciaService service) {
        this.service = service;
    }

    /**
     * 
     * @param ocorrenciaDTO
     * @return
     */
    @PostMapping("/create")
    public ResponseEntity<Ocorrencia> criarOcorrencia(@RequestBody CreateOcorrenciaDTO ocorrenciaDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criarOcorrencia(ocorrenciaDTO));
    }

    /**
     * 
     * @param ocorrencia
     * @return
     */
    @PutMapping("/atualizar")
    public ResponseEntity<String> atualizarOcorrencia(@RequestBody Ocorrencia ocorrencia) {
        service.atualizarOcorrencia(ocorrencia);
        return ResponseEntity.status(HttpStatus.CREATED).body("Dados atualizados com sucesso!");
    }

    /**
     * 
     * @param key
     * @return
     */
    @GetMapping("/verificar/{key}")
    public ResponseEntity<Ocorrencia> verificarStatus(@PathVariable String key) {
        return ResponseEntity.status(HttpStatus.OK).body(service.verificarOcorrencia(key));
    }
}
