# Extensão google - Gerênciamento de ocorrências 

## Objetivos e Arquitetura do projeto
- ## Descrição do projeto
    Esse projeto tem como principal objetivo evitar o lançamento de ocorrências específicas indevidamente. Para isso, quando é realizada a abertura de uma ocorrência no SSW, essa ação acarreta em uma mensagem de confirmação para que o responsável faça a liberação (ou não) da abertura da ocorrência.
- ## Diagrama detalhado de ações
![Diagrama](./public/DiagramaOcorrências.drawio.png)
- ## Diagrama da Arquitetura Global
```mermaid
    flowchart
        style ARQ fill:#121212,stroke:#326ce5,color:#fff,stroke-width:4px,stroke-dasharray: 5 5
        classDef app fill:#4a0e0e,stroke:#ff5555,color:#fff
            style APP fill:#260a0a,stroke:#ff5555,color:#fff,stroke-width:2px
        classDef amz fill:#1b5e20,stroke:#66bb6a,color:#fff
            style AMZ fill:#0a2610,stroke:#66bb6a,color:#fff,stroke-width:2px
        classDef svc fill:#0d47a1,stroke:#42a5f5,color:#fff
            style SVC fill:#051e3e,stroke:#42a5f5,color:#fff,stroke-width:2px
        direction LR
        subgraph ARQ[ARQUITETURA COMPLETA]
            PLT["`PLATAFORMA
            SSW`"]

            subgraph APP[SERVIÇOS]
                direction RL:::app
                API[API NEST.js]:::app
                EXT["`EXTENSÃO
                GOOGLE`"]:::app
                API ~~~ EXT
            end

            subgraph SVC[SERVIÇOS EXTERNOS]
                SCF[SacFlow]:::svc
            end

            subgraph AMZ[ARMAZENAMENTO]
                DB[(PostgreSql)]:::amz
            end

            PLT ~~~ APP ~~~ SVC ~~~ AMZ

            PLT <--"`1.Lançamento de Ocorrência`"--> EXT
            
            EXT --"2."POST/KEY--> API
            
            EXT <--"6.GET/STATUS"--> API
            
            API <--"`3.POST/KEY
            4.PUT/KEY+''SIM'' ou ''NÃO''`"--> SCF
            
            API --"`3.CREATE: 
            KEY + ''Aguardando''
            5.UPDATE:
            KEY + ''SIM'' ou ''NÃO''`"--> DB
            
        end
```
## **Extensão Google** - Ferramenta para controle de ocorrências SSW
- ### Descrição da aplicação
    Ferramenta do google para realizar, não só, alterações na página do SSW e bloqueio de envio de mensagem tamporário ou constânte, dependêndo da situação; mas também, realiza chamada HTTP para a API de comunicação com Banco de dados e SacFlow e aplicação de lógica de negócio.
    - **Resumo:** Responsável pela interface com o usuário no navegador e captura de dados.
- ### Ferramentas utilizadas e Pré-Requisitos Globais
    | Ferramenta | Descrição | Finalidade |
    | :--- | :--- | :--- |
    | [JavaScript](https://developer.mozilla.org/pt-BR/docs/Web/JavaScript) | Linguagem de programação de alto nível. | Implementação da lógica, manipulação de DOM e comunicação assíncrona. |
    | [HTML5](https://developer.mozilla.org/pt-BR/docs/Web/HTML) | Linguagem de marcação para web. | Estruturação da interface visual (Popups e Sidebars). |
    | [CSS3](https://developer.mozilla.org/pt-BR/docs/Web/CSS) | Mecanismo de estilização. | Design responsivo e identidade visual da extensão. |
    | [Manifest V3](https://developer.chrome.com/docs/extensions/mv3/intro/) | Documento de configuração do Chrome. | Gerenciamento de permissões e ciclo de vida da extensão. |

## **API NEST.js** - Ferramenta chamadas de requisições/ações externas
- ### Descrição da aplicação
    Aplicação que realiza a comunicação com banco de dados para armanezar a situação do status da UniqueKey gerada para cada chamado específico, chamar o SacFlow para envio de mensagem e aplicação de lógica de negócio.
    - **Resumo:** Responsável pelo processamento de dados, regras de negócio e persistência.
- ### Ferramentas utilizadas e Pré-Requisitos Globais
    | Ferramenta | Descrição | Finalidade |
    | :--- | :--- | :--- |
    | [Node.js](https://nodejs.org/) | Ambiente de execução JavaScript. | Base para execução do servidor backend. |
    | [Nest.js](https://nestjs.com/) | Framework progressivo para Node.js. | Arquitetura escalável e organizada do servidor. |
    | [TypeScript](https://www.typescriptlang.org/) | Superconjunto de JS com tipagem. | Segurança no desenvolvimento e melhor manutenção de código. |
    | [Prisma ORM](https://www.prisma.io/) | Mapeador objeto-relacional (ORM). | Interação produtiva e segura com o banco de dados. |
    | [SacFlow](https://sacflow.io/) | Plataforma de atendimento/automação. | Gestão de fluxos de conversas e integração de SAC. |

## Pré-Requisitos Globais
| Requisito | Versão Sugerida | Link para Download |
| :--- | :--- | :--- |
| **Node.js** | v18.x ou superior | [Download Node](https://nodejs.org/) |
| **NPM ou Yarn** | Gerenciador de pacotes | [NPM](https://www.npmjs.com/) |
| **Google Chrome** | Versão recente | [Download Chrome](https://www.google.com/chrome/) |
| **Docker** | Opcional (p/ banco de dados)**(Ainda não Incluindo no projeto)** | [Download Docker](https://www.docker.com/) |

## Como rodar o projeto:
1. Vá em gerênciar suas extensões, ative o modo Desenvolvedor, inicie a extensão do google selecionando "Carregar sem compactação" e carregando o arquivo "Projeto_ocorrencias_SSW";
    * A aplicação ainda não está finalizada, por isso ainda existe essa necessidade.
2. Criar .env com link do banco de dados na pasta "Projeto_ocorrencias_NESTjs";
3. Iniciar API Nest.js, navegando até a pasta "Projeto_ocorrencias_NESTjs", execute `npm install` e `npm run start:dev`.
    ```bash
    cd .\Projeto_ocorrencias_NESTjs\
    npm install
    npm run start:dev
    ```

## Próxima Fase (Melhorias):
- [] Durante o "Período comercial", é necessário criar uma "trava" que apenas manda a mensagem e não faz o bloqueio;
- [x] Incluir filial na chave; 
Capturar a URL e incluir em uma fila de processamento;
Opção de incluir uma VM para rodar a aplicação a parte;
Criar um buscar ocorrência sem parâmetros;
O que fazer quando existem muitas ocorrências seguida?
Valor dela e qual é o cliente ao lanãr uma ocorrência;
Necessidade de validação de ocorrência de acordo com o valor da produto;
Seleção de números específicos, respectivamente ao número da ocorrência;
Lançamento de apenas notificações ao realizar autorizações;
(ideia):Notificação de retorno de acordo com o tempo que o veiculo está parado;
Quando é bipada uma sobra ou falta, verificar se houve uma outra abertura de ocorrência para notificar que existe uma falta em algum lugar dessa carga;
Através da bipagem é possível localizar todas as cargas que existem?
Através da quantidade de volumes é possível encontrar a sobra de alguma falta?
77(falta)(Bloqueio de acordo com o valor) e 
30(avaria)(notificação)(bloqueio dependendo do usuário - se for da operação)