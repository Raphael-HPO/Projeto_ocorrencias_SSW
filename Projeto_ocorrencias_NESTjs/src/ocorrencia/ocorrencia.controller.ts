import { Body, Controller, Get, Param, Post, Put } from '@nestjs/common';
import { OcorrenciaService } from './ocorrencia.service';
import { Ocorrencia, EnvOcorrencia } from 'src/dto/ocorrencia.dto';

@Controller('ocorrencia')
export class OcorrenciaController {
    constructor(private readonly service: OcorrenciaService) { }

    /**
     * Extensão >>> API >>> SACFLOW
     * @param ocorrencia 
     * @returns 
     */
    @Post('create')
    async create(@Body() ocorrencia: EnvOcorrencia) {
        const salveBanco = await this.service.criarOcorrencia(ocorrencia);
        const mensagem = await this.service.enviarWapp(ocorrencia);
        console.log("Controller", salveBanco.retorno, mensagem)
        return mensagem;
    }

    /**
     * Extensão >>> API
     * @param key 
     * @returns 
     */
    @Get('verificar/:key')
    async verificarOcorrencia(@Param('key') key: string): Promise<Ocorrencia> {
        return await this.service.verificarOcorrencia(key);
    }

    /**
     * SACFLOW >>> API
     * @param ocorrencia 
     * @returns 
     */
    @Put('atualizar')
    async atualizarStatusOcorrencia(@Body() ocorrencia: Ocorrencia) {
        const retorno = this.service.atualizarOcorrencia(ocorrencia);
        console.log("Controller:retorno da atualização" + retorno)
        return retorno;
    }
}
