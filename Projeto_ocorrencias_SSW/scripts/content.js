console.log("Extensão Ativa...");
/**
 * Ações que serão realizadas de acordo com seus respectivos códigos de ocorrência.
 */
const acoes = {
    81: () => {
        const service = new ServicoDOM();
        const dadosOcorrencia = service.buscarDadosOcorrencia(81, 'CHEGADA NA PORTARIA DO DESTINATARIO').objDadosOcorrencia;
        const unicKeyConcat = new String().concat([dadosOcorrencia.usuario.nome, dadosOcorrencia.codCTRC, dadosOcorrencia.codOcorrencia, dadosOcorrencia.dataCriacao, dadosOcorrencia.horaCriacao]);
        const unicKey = unicKeyConcat.replaceAll("-", "").replace(/[,.:\-/]/g, '');
        service.spamStatus("pendente");
        console.log("KEY 81" + unicKey)
        sendBack(dadosOcorrencia);
        return true;
    },
    77: () => {
        console.log("ACAO 77");
        sendBack(new ServicoDOM().buscarDadosOcorrencia(77).jsonDadosOcorrencia)
    },
};

//Construtores: Model dos dados do usuário.
class DadosUsuario {
    constructor(nome, filial) {
        this.nome = nome;
        this.filial = filial;
    }
}

//Construtores: Model dos dados da ocorrência.
class DadosOcorrencia {
    constructor(codOcorrencia, dsOcorrencia, codCTRC, data, hora, usuario) {
        this.codOcorrencia = codOcorrencia;
        this.dsOcorrencia = dsOcorrencia;
        this.codCTRC = codCTRC;
        this.dataCriacao = data;
        this.horaCriacao = hora;
        this.usuario = usuario
    }
}

class ServicoDOM {
    /**
     * Faz a busca dos dados e inclue em seus respectivos atributos, criando assim o objeto completo.
     * @param {*} codOcorrencia Código da ocorrência analizada
     * @param {*} dsOcorrencia Descrição drespectiva à ocorrência indicada
     * @returns 
     */
    buscarDadosOcorrencia(codOcorrencia, dsOcorrencia) {
        console.log("Localizando dados...");
        const dadosUsuario = document.querySelectorAll('#infodiv b')[1].textContent;
        const filialUsuario = dadosUsuario.replace(/\u00A0/g, " ").trim().split(' ')[0];
        const nomeUsuario = dadosUsuario.replace(/\u00A0/g, " ").trim().split(' ')[1];
        const UsuarioCriador = new DadosUsuario(nomeUsuario, filialUsuario);

        const codCTRC = document.getElementById('1').textContent;
        const dataCriacao = document.getElementById('4').value;
        const horaCriacao = document.getElementById('5').value;
        const objDadosOcorrencia = new DadosOcorrencia(
            codOcorrencia,
            dsOcorrencia,
            codCTRC,
            dataCriacao,
            horaCriacao,
            UsuarioCriador
        );
        const jsonDadosOcorrencia = JSON.stringify(objDadosOcorrencia);
        return { objDadosOcorrencia, jsonDadosOcorrencia };
    }

    //
    /**
     * realiza a busca da função correspondente ao númeroi da ocorrência.
     * @param {*} numeroOcorrencia 
     * @param {*} acoes 
     */
    acaoPorOcorrencia(numeroOcorrencia, acoes) {
        const acao = acoes[parseInt(numeroOcorrencia)];
        if (typeof acao == 'function') {
            acao();
        }
    }

    //
    /**
     * Função aplicada internamente para verificar caso a função correspondente esteja na lista "ações".
     * @param {*} numeroOcorrencia Número da ocorrência disponibilizado pelo SSW
     * @param {*} acaoOcorrencia Ação(es)(Função(es)) que serão realizas caso essa ocorrência seja acionada
     */
    static verificarOcorrencia(numeroOcorrencia, acaoOcorrencia) {
        const input = document.getElementById('3');
        input.addEventListener('input', (preenchimento) => {
            if (parseInt(preenchimento.target.value) == parseInt(numeroOcorrencia)) {
                console.log(`Ocorrencia:${numeroOcorrencia} encontrada.`)
                acaoOcorrencia(numeroOcorrencia);
            }
        });
    }

    /**
     * Método que aplica uma mensagem diferente dependendo do parâmetro inserido. Possíveis parâmetros
     * @param {*} status Possíveis preenchimentos: pendente, autorizado, negado, sem-retorno
     */
    spamStatus(status) {
        const div = document.createElement('div');
        const form = document.getElementById('frm');

        div.id = 'spamStatus';
        div.className = 'texto';
        div.style.position = "absolute";
        div.style.left = "320px";
        div.style.top = "240px";
        if (status == "pendente") {
            div.textContent = "Solicitação sendo avaliada! Aguarde...";
            div.style.color = 'orange';
        } else if (status == "autorizado") {
            div.textContent = "Solicitação Autorizada!";
            div.style.color = 'green';
        } else if (status == "negado") {
            div.textContent = "Solicitação Negada!";
            div.style.color = 'red';
        } else if (status == "sem-retorno") {
            div.textContent = "Sem Retorno...";
            div.style.color = 'red';
        }

        form.appendChild(div);
    }

    /**
     *  Função de chamada de API
     *  Realiza a chamada do backgroud para fazer a ação de chamar a API e capturar o retorno
     * 
     * @param {*} unicKey 
     * @returns resposta da API
     */

    async getVerifique(unicKey) {
        return new Promise((resolve) => {
            chrome.runtime.sendMessage(
                {
                    action: "getToAPI",
                    key: unicKey
                },
                (response) => {
                    console.log("RESPOSTA DO BACKGROUND:", response);
                    resolve(response);
                }
            );
        });
    }

    /**
     * Recebe o resultado do "POST" ou "GET" trazendo o resultado da validação.
     * Esses dados são trazidos apartir de uma requisição feita pelo "BackGround".
     * @returns Booleano dependêndo do retorno da API
     */

    //
    async solicitacaoDeOcorrencia() {
        //Lógica para solicitar a liberação de ocorrência
        const dados = this.buscarDadosOcorrencia().objDadosOcorrencia;
        const unicKeyConcat = new String().concat([dados.usuario.nome, dados.codCTRC, dados.codOcorrencia, dados.dataCriacao, dados.horaCriacao]);
        const unicKey = unicKeyConcat.replaceAll("-", "").replace(/[,.:\-/]/g, '');
        const retorno = this.getVerifique(unicKey);

        if (retorno == "Sim") {
            document.getElementById("spamStatus").remove();
            this.spamStatus('autorizado');
            document.removeEventListener('click', acaoClick, true);
            document.getElementById('9').click();
            return true;
        } else if (retorno == "Nao") {
            this.spamStatus("negado");
            return true;
        } else if (retorno == "Aguardando") {
            return false;
        } else {
            return false;
        }
    }

    /**
     * Lógica de acionamento de "ação Por Ocorrência" e bloqueador do botão
     * */
    liberacaoDeOcorrencia(acao) {
        const link = acao.target.closest("a");
        let contador = 0;
        if (link && link.getAttribute("href") == "#" && acao.target.id == document.getElementById('9').id) {
            if (contador < 1) {
                contador += 1;
                const inputAtual = document.getElementById('3').value.trim();
                if (inputAtual) {
                    this.acaoPorOcorrencia(inputAtual, acoes);
                } else {
                    console.log("Campo Vazio")
                }
                acao.preventDefault();
                acao.stopImmediatePropagation();
            } else {
                //feat: Aplicar função, caso o botão seja clicado várias vezes, mostrando mensagem.
                acao.preventDefault();
                acao.stopImmediatePropagation();
            }
        }
    }
}

const ativo = true;
async function main() {
    const aButton = document.getElementById('9');
    const input = document.getElementById('3');

    if (input && ativo) {
        const service = new ServicoDOM();
        const dadosOcorrencia = service.buscarDadosOcorrencia(81, 'CHEGADA NA PORTARIA DO DESTINATARIO').objDadosOcorrencia;
        const unicKeyConcat = `${dadosOcorrencia.usuario.nome}${dadosOcorrencia.codCTRC}${dadosOcorrencia.codOcorrencia}${dadosOcorrencia.dataCriacao}${dadosOcorrencia.horaCriacao}`
        const unicKey = unicKeyConcat.replaceAll("-", "").replace(/[,.:\-/]/g, '');
        console.log("KEY MAIN" + unicKey)
        const acaoClick = (acao) => service.liberacaoDeOcorrencia(acao);
        document.addEventListener('click', acaoClick, true);
        if (unicKey.length >= 22) {
            const verificarRetorno = setInterval(async () => {
                const dadosRetorno = await service.getVerifique(unicKey);
                if (dadosRetorno.retorno === "Sim") {
                    clearInterval(verificarRetorno);
                    document.getElementById("spamStatus").remove();
                    service.spamStatus('autorizado');
                    document.removeEventListener('click', acaoClick, true);
                    document.getElementById('9').click();
                } else if (dadosRetorno.retorno === "Nao") {
                    clearInterval(verificarRetorno);
                    document.getElementById("spamStatus").remove();
                    service.spamStatus("negado");
                } else {
                    console.warn("dadosRetorno is undefined, null, or its 'retorno' property is not 'Sim'.", dadosRetorno);
                }
            }, 10000);

        }
        return true;
    }
    return false;
}

/**
 * Ação realizada devido a geração de página vazia e depois preenchimento.
 */
const verificarExistencia = setInterval(() => {
    if (main()) {
        clearInterval(verificarExistencia);
    }
}, 1000);

/**
 * Envio de dados para o background(Onde são realizadas as ações.)
 * @param {*} valueJson Json que será enviado para as APIs
 */
function sendBack(valueJson) {
    console.log("mensagem sendo enviada ao BackGround...", valueJson);
    chrome.runtime.sendMessage(valueJson);
}