package br.com.ativa.ocorrencias.DTO;

//TODO: Incluir validações
public record CreateOcorrenciaDTO(
                // String cliente,
                String codCTRC,
                String codOcorrencia,
                // Double valor,
                String dsOcorrencia,
                String dataCriacao,
                String horaCriacao,
                String key,
                Usuario usuario) {
}
