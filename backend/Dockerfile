# Basis-Image mit Java 21 JDK
FROM eclipse-temurin:21-jdk-alpine

# Arbeitsverzeichnis im Container
WORKDIR /app

# JAR-Datei ins Image kopieren
COPY target/WeatherWeb-0.0.1-SNAPSHOT.jar app.jar

# Port freigeben (falls du z. B. 8081 verwendest, hier ändern)
EXPOSE 8080

# Startbefehl
ENTRYPOINT ["java", "-jar", "app.jar"]
