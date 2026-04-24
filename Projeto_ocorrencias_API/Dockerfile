#FASE 1 - Inclusão da imagem do mavem e inclusão do arquivo POM.xml e SRC
FROM maven:3.9.6-eclipse-temurim-21 AS build

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline

COPY src ./src
#Isso terá que ser ajustado depois que for necessário rodar osw testes automáticos com JUnit, Mockito e github actions
RUN mvn clean package -DskipTests

#FASE 2 - Inclusão da imagem java e compilação do código

FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY --from=build /app/target/ocorrencias-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 3000

ENTRYPOINT ["java","-jar","app.jar" ]