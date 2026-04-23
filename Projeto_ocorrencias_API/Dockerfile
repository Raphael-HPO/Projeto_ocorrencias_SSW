#Incluir imagem do maven e fazer repositório de trabalho
FROM maven:3.9-eclipse-temurin-21-alpine AS build

WORKDIR /app
#Copiar todos os dados presentes na pasta do dockerfile e copiar para a ./app criada no SO
COPY . /app/

FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 3000

ENTRYPOINT ["java","-jar","app.jar"]