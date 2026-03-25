chrome.runtime.onMessage.addListener((request, sender, sendResponse) => {
    console.log("Mensagem Recebida!")
    /**
     * Lançamento de dados para a API Nest realizadar o envio no whatsapp e armazenar no banco
     * @returns status da realização do fetch
     */
    async function postToAPI() {
        const unicKeyConcat = `${request.usuario.nome}${request.codCTRC}${request.codOcorrencia}${request.dataCriacao}${request.horaCriacao}`
        const unicKey = unicKeyConcat.replaceAll("-", "").replace(/[,.:\-/]/g, '');
        try {
            const response = await fetch("https://nonvehemently-elmier-jordynn.ngrok-free.dev/ocorrencia/create", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    "codCTRC": request.codCTRC,
                    "codOcorrencia": request.codOcorrencia,
                    "dsOcorrencia": request.dsOcorrencia,
                    "dataCriacao": request.dataCriacao,
                    "horaCriacao": request.horaCriacao,
                    "key": unicKey,
                    "usuario": {
                        "nome": request.usuario.nome,
                        "filial": request.usuario.filial
                    }
                })
            });
            if (!response.ok) {
                throw new Error(`Erro HTTP: ${response.status}`);
            } else {
                const dados = await response.json();
                console.log("dados enviados para API:")
                console.log(dados)
                return dados;
            }
        } catch (e) {
            console.error("Erro ao chamar a API" + e);
            return null;
        }

    }

    if (request.action === "getToAPI") {
        getRetorno(request.key, sendResponse);
        return true;
    }
    postToAPI();
});
/**
 * Solicitação de consulta no banco de dados para receber o status.
 * @param {*} unicKey Chave única criada a partir do dados do ob jeto enviado para a API Nest.js
 * @param {*} sendResponse Resposta da consulta que será enviada para o content
 * @returns 
 */
async function getRetorno(unicKey, sendResponse) {
    try {
        const response = await fetch(`https://nonvehemently-elmier-jordynn.ngrok-free.dev/ocorrencia/verificar/${unicKey}`, {
            method: 'GET',
            headers: { "ngrok-skip-browser-warning": "true" }
        });
        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(`HTTP Error: ${response.status} - ${response.statusText}. Server message: ${errorText}`);
        }
        const dados = await response.json();
        console.log(dados);
        sendResponse(dados);
        return dados;
    } catch (e) {
        console.error("Erro na API:", e);
        sendResponse({ erro: true, mensagem: e.message });
    }
}