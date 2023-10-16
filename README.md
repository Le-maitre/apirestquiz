# apirestquiz
# Documentation de l'API API-REST-QUIZ

Cette documentation vous guidera à travers les étapes nécessaires pour utiliser et développer l'API de Quiz créée avec Spring Boot.

## Description de l'API

L'API Quiz permet aux utilisateurs de créer et de jouer à des quiz sur différents domaines de connaissance. Elle expose des endpoints pour gérer les quiz, les questions, les réponses, etc.

## Configuration de la Base de Données

1. Assurez-vous d'avoir [XAMPP](https://www.apachefriends.org/index.html) installé sur votre système.

2. Lancez XAMPP et démarrez les services Apache et MySQL.

3. Ouvrez [phpMyAdmin](http://localhost/phpmyadmin) dans votre navigateur.

4. Créez une nouvelle base de données en utilisant le nom défini dans le fichier `application.properties` de votre application Spring Boot. Par défaut, le nom de la base de données est `apirestquiz`. Assurez-vous que le nom de la base de données correspond à celui configuré dans le fichier `application.properties`.

## Configuration de l'IDE IntelliJ

1. Ouvrez IntelliJ IDEA.

2. Importez le projet depuis votre dépôt GitHub.

3. Configurez le projet pour utiliser Maven.

4. Assurez-vous que le fichier `application.properties` contient les bonnes configurations pour la base de données, notamment le nom de la base de données.

5. Exécutez l'application en cliquant sur le bouton "Run" (ou en utilisant la commande Maven `mvn spring-boot:run`).

## Accès à Swagger

Swagger est un outil de documentation pour les API REST qui vous permet de tester et d'explorer les endpoints de votre API. Voici comment y accéder :

1. Après avoir lancé l'application, ouvrez un navigateur web.

2. Accédez à Swagger en utilisant l'URL suivante : [http://localhost:9000/swagger-ui.html](http://localhost:9000/swagger-ui.html).

3. Vous verrez la documentation Swagger avec la liste des endpoints et des opérations disponibles.

## Exemples d'Endpoints

Voici quelques exemples d'endpoints que vous pouvez utiliser pour interagir avec l'API :

- `GET api/quizzes`: Récupérer la liste des quiz disponibles.
- `POST api/users/{userId}/quizzes`: Créer un nouveau quiz.
- `GET api/quizzes/{id}`: Récupérer un quiz par son ID.
- `PUT api/users/{userId}/quizzes/{quizId}`: Mettre à jour un quiz existant.
- `DELETE api/users/{userId}/quizzes/{quizId}`: Supprimer un quiz.

## Contribuer

Si vous souhaitez contribuer à l'amélioration de cette API, n'hésitez pas à cloner ce dépôt, à apporter des modifications et à soumettre des pull requests.

## Licence

Ce projet est sous licence [MIT](LICENSE.md). Vous êtes libre de l'utiliser, de le modifier et de le distribuer conformément aux termes de cette licence.
