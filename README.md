🕹️ ArrasGames – Application Java (Client lourd)
Ce projet est une application Java client lourd développée dans le cadre du BTS SIO – Épreuve E6.
Elle permet à un utilisateur de se connecter, de saisir un code forfait, puis d’afficher et suivre le temps restant de sa session.

📁 Structure du projet
Une fois le projet téléchargé, l’arborescence est la suivante :

ArrasGames.jar → Application exécutable.

cake_arrasgames-db_2025-03-24.sql → Script SQL pour créer la base de données.

src/ → Contient les fichiers sources :

Connexion.java → Gestion de la connexion à la base.

Login.java, Main.java, InterfaceForfait.java, InterfaceTemps.java → Interfaces graphiques et logique applicative.

jbcrypt-0.4.jar → Dépendance pour le hachage des mots de passe.

mysql-connector-j-9.2.0.jar → Connecteur JDBC pour MySQL.

✅ Prérequis
Java 17 ou version supérieure installé.

Serveur MySQL ou MariaDB opérationnel.

Outil de gestion de base de données (ex : DBeaver, phpMyAdmin).

⚙️ Installation
Créer une base de données (par exemple arrasgames_db).

Importer le fichier cake_arrasgames-db_2025-03-24.sql dans votre base via un outil MySQL.

Dans le fichier Connexion.java, modifier si besoin les identifiants de connexion :


String url = "jdbc:mysql://localhost:3306/arrasgames_db";
String user = "root";
String password = "votre_mot_de_passe";
Lancer l’application avec la commande :


java -jar ArrasGames.jar
🧩 Fonctionnalités
Authentification de l’utilisateur.

Saisie d’un code forfait.

Affichage en temps réel du temps restant.

Sécurisation des mots de passe avec jBCrypt.
