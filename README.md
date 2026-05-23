# Frontoffice Reservation

Application web Spring Boot dédiée au front-office d'un système de réservation. Elle permet de consulter les réservations issues du back-office, de les filtrer par période et d'afficher le plan d'assignation des véhicules pour une date donnée.

## Fonctionnalités

- Consultation des réservations enregistrées dans le back-office.
- Filtrage des réservations par date de début et date de fin.
- Affichage des informations clés: client, nombre de passagers, date d'arrivée et lieu.
- Consultation du plan d'assignation des véhicules par date.
- Regroupement des trajets assignés par véhicule.
- Affichage des réservations non assignées et des véhicules disponibles.
- Gestion des erreurs d'appel API et des tokens invalides.

## Stack technique

- Java 17
- Spring Boot
- Spring MVC
- Thymeleaf
- RestTemplate
- Jackson
- Maven

## Architecture

Le projet joue le rôle de front-office et communique avec une API back-office externe.

```text
Utilisateur
   |
   v
Frontoffice Reservation
   |
   v
Backoffice Reservation API
```

Principaux dossiers:

- `src/main/java/com/frontoffice/controller`: contrôleurs web.
- `src/main/java/com/frontoffice/client`: client HTTP vers le back-office.
- `src/main/java/com/frontoffice/dto`: objets de transfert de données.
- `src/main/resources/templates`: vues Thymeleaf.
- `src/main/resources/static`: fichiers CSS et ressources statiques.

## Configuration

La configuration principale se trouve dans `src/main/resources/application.properties`.

```properties
server.port=${PORT:8081}
backoffice.base-url=${BACKOFFICE_BASE_URL:http://localhost:8080/BackofficeReservation}
frontoffice.api-token=tok_valid_123
```

Variables importantes:

- `PORT`: port HTTP du front-office, `8081` par défaut.
- `BACKOFFICE_BASE_URL`: URL de base de l'API back-office.
- `frontoffice.api-token`: token utilisé pour authentifier les appels vers le back-office.

## Lancement local

Prérequis:

- Java 17 installé.
- Le back-office démarré et accessible.

Lancer l'application:

```bash
./mvnw spring-boot:run
```

Sous Windows:

```bash
mvnw.cmd spring-boot:run
```

L'application sera disponible par défaut à l'adresse:

```text
http://localhost:8081/home
```

## Pages principales

- `/home`: page d'accueil.
- `/reservations`: liste et filtre des réservations.
- `/assignation/form`: formulaire de sélection de date pour l'assignation.
- `/assignation/plan?date=YYYY-MM-DD`: résultat de l'assignation pour une date.

## API back-office attendue

Le front-office consomme principalement:

- `GET /api/reservations`: récupération des réservations.
- `GET /api/plan-date?date=YYYY-MM-DD`: récupération du plan d'assignation.
- `POST /api/plan-date`: génération/récupération du plan via formulaire encodé.

## Objectif du projet

Ce projet met en place une interface claire pour les équipes front-office afin de suivre les réservations et visualiser rapidement l'organisation des véhicules. Il sert de couche de présentation entre les utilisateurs opérationnels et le module back-office de gestion des réservations.
