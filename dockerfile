# Usa una imagen base con Java 17
FROM eclipse-temurin:17-jdk-alpine

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo JAR generado al contenedor
COPY target/auth-server.jar app.jar

# Copia también el script wait-for-it.sh
COPY wait-for-it.sh wait-for-it.sh

# Expone el puerto en el que se ejecuta la aplicación
EXPOSE 8082

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
