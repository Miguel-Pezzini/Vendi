# Use a imagem oficial do OpenJDK
FROM openjdk:17-jdk-slim

# Defina o diretório de trabalho no contêiner
WORKDIR /app

# Copie o arquivo JAR para dentro do contêiner
COPY out/artifacts/vendi_jar/vendi.jar app.jar

# Exponha a porta que o Spring Boot vai rodar
EXPOSE 8080

# Execute a aplicação Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]