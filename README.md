# Extensão google - Gerênciamento de ocorrências 

## Objetivos e Arquitetura do projeto
- ## Descrição do projeto
- ## Diagrama detalhado de ações
![Diagrama](./public/DiagramaOcorrências.drawio.png)
- ## Diagrama da Arquitetura Global
```mermaid
    flowchart
        direction LR
        subgraph ARQ[ARQUITETURA COMPLETA]
            PLT["`PLATAFORMA
            SSW`"]

            subgraph APP[SERVIÇOS]
                direction RL
                API[API NEST.js]
                EXT["`EXTENSÃO
                GOOGLE`"]
                API ~~~ EXT
            end

            subgraph SVC[SERVIÇOS EXTERNOS]
                SCF[SacFlow]
            end

            subgraph AMZ[ARMAZENAMENTO]
                DB[(PostgreSql)]
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
- ### Ferramentas utilizadas e Pré-Requisitos Globais

## **API NEST.js** - Ferramenta chamadas de requisições/ações externas
- ### Descrição da aplicação
- ### Ferramentas utilizadas e Pré-Requisitos Globais