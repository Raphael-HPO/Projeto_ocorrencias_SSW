package br.com.ativa.ocorrencias.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import br.com.ativa.ocorrencias.DTO.CreateOcorrenciaDTO;
import br.com.ativa.ocorrencias.client.OcorrenciaClient;
import br.com.ativa.ocorrencias.client.DTOClient.ContatoDestinatarioDTO;
import br.com.ativa.ocorrencias.client.DTOClient.ContextoDTO;
import br.com.ativa.ocorrencias.client.DTOClient.MensagemDTO;
import br.com.ativa.ocorrencias.client.DTOClient.MetadataDTO;
import br.com.ativa.ocorrencias.client.DTOClient.OcorrenciaClientExternoDTO;
import br.com.ativa.ocorrencias.client.DTOClient.SessaoDTO;
import br.com.ativa.ocorrencias.exceptions.MensagemWhatsappException;
import br.com.ativa.ocorrencias.exceptions.OcorrenciaNaoEncontradaException;
import br.com.ativa.ocorrencias.model.entity.Ocorrencia;
import br.com.ativa.ocorrencias.model.enums.Retorno;
import br.com.ativa.ocorrencias.repository.OcorrenciaRepository;
import jakarta.transaction.Transactional;

@Service
public class OcorrenciaService {
        OcorrenciaRepository repository;
        OcorrenciaClient client;

        OcorrenciaService(OcorrenciaRepository repository, OcorrenciaClient client) {
                this.repository = repository;
                this.client = client;
        }

        /**
         * Cria uma nova ocorrência no Banco de dados e Chama a função de envio de
         * mensagem para o Whatsapp.
         * 
         * @param ocorrenciaDTO
         * @return
         * @throws
         */
        @Transactional
        public Ocorrencia criarOcorrencia(CreateOcorrenciaDTO ocorrenciaDTO) {
                Ocorrencia ocorrencia = new Ocorrencia(ocorrenciaDTO.key(), Retorno.Aguardando);
                Ocorrencia ocorrenciaSalva = repository.save(ocorrencia);
                this.enviarMensagemWhatsApp(ocorrenciaDTO);
                return ocorrenciaSalva;
        }

        /**
         * Recebe a ocorrência e atualiza no banco de dados o status da ocorrência, caso
         * a ocorrência não exista lança uma exceção
         * 
         * @param ocorrencia
         * @return
         * @throws
         */
        public Ocorrencia atualizarOcorrencia(Ocorrencia ocorrencia) {
                Ocorrencia ocorrenciaExistente = this.verificarOcorrencia(ocorrencia.getKey());
                ocorrenciaExistente.setRetorno(ocorrencia.getRetorno());
                return repository.save(ocorrenciaExistente);
        }

        /**
         * //TODO: Incluir dados sensíveis em variáveis de ambiente
         * //TODO: Implementar lógica para envio de mensagens dinâmicas, utilizando os
         * //dados por setor para enviar as mensagens de forma personalizada, utilizando
         * //os templates do WhatsApp Business API
         * 
         * @param ocorrenciaDTO
         * @throws
         */
        @Async
        public void enviarMensagemWhatsApp(CreateOcorrenciaDTO ocorrenciaDTO) {
                client.envioMensagemWhatsApp("Bearer b65f1107-9206-4207-b3bc-e31caf9b476a",
                                new OcorrenciaClientExternoDTO(15709L, 629L, "SSW",
                                                new ContatoDestinatarioDTO("Raphael.O", "5511951166249"),
                                                new MensagemDTO("template", "abertura_ocorrencia",
                                                                new ArrayList<>(List.of(
                                                                                ocorrenciaDTO.usuario().getNome(),
                                                                                ocorrenciaDTO.codOcorrencia(),
                                                                                ocorrenciaDTO.dsOcorrencia(),
                                                                                ocorrenciaDTO.usuario().getFilial(),
                                                                                ocorrenciaDTO.dataCriacao(),
                                                                                ocorrenciaDTO.horaCriacao())),
                                                                new MetadataDTO(ocorrenciaDTO.key())),
                                                1638L,
                                                new SessaoDTO(new ContextoDTO(ocorrenciaDTO.key()))))
                                .orElseThrow(() -> new MensagemWhatsappException(401,
                                                "Erro ao chamar API de mensagem Whatsapp."));
        }

        /**
         * Retorna o status correspondente à ocorrência indicada.
         * 
         * @param key
         * @return Objeto Ocorrencia encontrado.
         * @throws OcorrenciaNaoEncontradaException Caso a chave indicada não tenha
         *                                          nenhum corrêspondente no banco de
         *                                          dados, será lançado o Exception.
         */
        public Ocorrencia verificarOcorrencia(String key) {
                return repository.findById(key)
                                .orElseThrow(() -> new OcorrenciaNaoEncontradaException("Ocorrência não encontrada."));
        }
}
