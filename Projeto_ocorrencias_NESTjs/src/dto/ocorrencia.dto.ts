import { env } from "process";

export class Ocorrencia {
    key: string;
    retorno: string;
}

class Usuario {
    nome: string;
    filial: string
}

export class EnvOcorrencia {
    codCTRC: string;
    codOcorrencia: string;
    dsOcorrencia: string;
    dataCriacao: string;
    horaCriacao: string;
    key: string;
    usuario: Usuario;
}