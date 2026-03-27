import { HttpService } from '@nestjs/axios';
import { Injectable, NotFoundException } from '@nestjs/common';
import { Ocorrencia, EnvOcorrencia } from 'src/dto/ocorrencia.dto';
import { PrismaService } from 'src/prisma/prisma.service';

@Injectable()
export class OcorrenciaService {
    constructor(private service: PrismaService
    ) { }
    /**
     * 
     * @param ocorrencia 
     * @returns 
     */
    async enviarWapp(ocorrencia: EnvOcorrencia): Promise<any> {
        try {
            const response = await fetch("https://api.sacflow.io/api/send-message", {
                method: "POST",
                headers: {
                    "Authorization": "Bearer b65f1107-9206-4207-b3bc-e31caf9b476a",
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    "accountId": 15709,
                    "channelId": 629,
                    "from": "SSW",
                    "contact": {
                        "name": "Raphael.O",
                        "phone": "5511951166249"
                    },
                    "message": {
                        "type": 'template',
                        "name": 'abertura_ocorrencia',
                        "variables": [`${ocorrencia.usuario.nome}`,
                        `${ocorrencia.codOcorrencia}`,
                        `${ocorrencia.dsOcorrencia}`,
                        `${ocorrencia.usuario.filial}`,
                        `${ocorrencia.dataCriacao}`,
                        `${ocorrencia.horaCriacao}`],
                        "metadata": {
                            "key": `${ocorrencia.key}`
                        }
                    },
                    "responseFlowId": 1638,
                    "session": {
                        "context": {
                            "key": `${ocorrencia.key}`
                        }
                    }
                })
            })
            return await response.json();
        } catch (e) {
            console.error(e);
        }
    }
    /**
     * 
     * @param ocorrencia 
     * @returns 
     */
    async criarOcorrencia(ocorrencia: EnvOcorrencia) {
        const resultado = this.service.ocorrencia.create({
            data: {
                key: ocorrencia.key
            }
        });

        return resultado;
    }

    /**
     * 
     * @param keySolicit 
     * @returns 
     */
    async verificarOcorrencia(keySolicit: string): Promise<Ocorrencia> {
        const ocorrencia = await this.service.ocorrencia.findFirst({
            where: {
                key: keySolicit
            }
        });
        if (!ocorrencia) {
            throw new NotFoundException("Key não Encontrada.")
        }
        return ocorrencia;
    }
    /**
     * 
     * @param ocorrencia 
     * @returns 
     */
    async atualizarOcorrencia(ocorrencia: Ocorrencia): Promise<any> {
        const atualizacao = await this.service.ocorrencia.update({
            where: {
                key: ocorrencia.key
            },
            data: {
                retorno: ocorrencia.retorno
            }
        })
        console.log("Service:Atualização Ocorrência", atualizacao.key, atualizacao.retorno)
        return atualizacao;
    }

}
