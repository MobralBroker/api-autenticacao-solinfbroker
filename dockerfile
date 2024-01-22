# Estágio 1: Construção do JAR usando Maven
FROM maven:3.8.4-openjdk-17 AS builder

WORKDIR /api-autenticacao

# Copia apenas o arquivo POM para obter as dependências
COPY pom.xml .

# Baixa as dependências do Maven
RUN mvn dependency:go-offline

# Copia o resto do código e realiza o build
COPY src src
RUN mvn package -DskipTests

# Estágio 2: Criação da imagem final com o JAR construído
FROM openjdk:17-jdk-alpine

WORKDIR /api-autenticacao

# Copia apenas o JAR construído do estágio anterior
COPY --from=builder /api-autenticacao/target/*.jar app.jar

EXPOSE 8080

# Config limit app java
ENV JAVA_OPTS="-XX:MinRAMPercentage=60 -XX:MaxRAMPercentage=90"

# Comando para executar a aplicação quando o contêiner for iniciado
CMD ["java", "-jar", "app.jar"]