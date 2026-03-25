import { Body, Controller, Get, Param, Post, Put } from '@nestjs/common';
import { OcorrenciaService } from './ocorrencia.service';
import { Ocorrencia, EnvOcorrencia } from 'src/dto/ocorrencia.dto';

@Controller('ocorrencia')
export class OcorrenciaController {
    constructor(private readonly service: OcorrenciaService) { }

    //Extensão >>> API >>> SACFLOW
    @Post('create')
    async create(@Body() ocorrencia: EnvOcorrencia) {
        const salveBanco = await this.service.criarOcorrencia(ocorrencia);
        const mensagem = await this.service.enviarWapp(ocorrencia);
        console.log(salveBanco + "\n" + mensagem)
        return mensagem;
    }

    //Extensão >>> API
    @Get('verificar/:key')
    async verificarOcorrencia(@Param('key') key: string): Promise<Ocorrencia> {
        return await this.service.verificarOcorrencia(key);
    }

    //SACFLOW >>> API
    @Put('atualizar')
    async atualizarStatusOcorrencia(@Body() ocorrencia: Ocorrencia) {
        const retorno = this.service.atualizarOcorrencia(ocorrencia);
        console.log(retorno)
        return retorno;
    }
}
