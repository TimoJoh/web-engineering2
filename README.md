# Wetterfrosch - Web Application
Gruppenmitglieder: Omer Butt, Benjamin Peiter, Timo Johannsen

Arbeitsaufteilung: 
  - Frontend: Omer Butt, Timo Johannsen
  - Backend: Omer Butt, Benjamin Peiter
  - Pair-Programming
---

## Frontend
### Starten
1. cd .\frontend\
2. npm install
3. npm start

-> Läuft auf http://localhost:3000/

## Backend
### Starten
1.  Maven Projekt initialisieren
    - pom.xml -> rechts click -> maven -> sync project
2. backend/src/main/java/com.weatherApp.WeatherWeb.api/
3. WeatherWebApplication starten

-> Läuft auf http://localhost:8080/

### Swagger UI Documentation
http://localhost:8080/swagger-ui/index.html

### H2 Datenbank
http://localhost:8080/h2-console
1. jdbc:h2:file:./Database/weatherDB ins JDBC URL Feld
2. User Name: sa
3. kein Passwort
