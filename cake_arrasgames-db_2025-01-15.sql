-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : mer. 15 jan. 2025 à 10:49
-- Version du serveur : 8.2.0
-- Version de PHP : 8.3.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `cake_arrasgames`
--

-- --------------------------------------------------------

--
-- Structure de la table `cake_d_c_users_phinxlog`
--

CREATE TABLE `cake_d_c_users_phinxlog` (
  `version` bigint NOT NULL,
  `migration_name` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `start_time` timestamp NULL DEFAULT NULL,
  `end_time` timestamp NULL DEFAULT NULL,
  `breakpoint` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `cake_d_c_users_phinxlog`
--

INSERT INTO `cake_d_c_users_phinxlog` (`version`, `migration_name`, `start_time`, `end_time`, `breakpoint`) VALUES
(20150513201111, 'Initial', '2024-11-13 09:17:11', '2024-11-13 09:17:12', 0),
(20161031101316, 'AddSecretToUsers', '2024-11-13 09:17:12', '2024-11-13 09:17:12', 0),
(20190208174112, 'AddAdditionalDataToUsers', '2024-11-13 09:17:12', '2024-11-13 09:17:12', 0),
(20210929202041, 'AddLastLoginToUsers', '2024-11-13 09:17:12', '2024-11-13 09:17:13', 0),
(20240328135459, 'CreateFailedPasswordAttempts', '2024-11-13 09:17:13', '2024-11-13 09:17:13', 0),
(20240328215332, 'AddLockoutTimeToUsers', '2024-11-13 09:17:13', '2024-11-13 09:17:13', 0),
(20240801112143, 'ChangeAvatarColumnTypeInSocialAccounts', '2024-11-13 09:17:13', '2024-11-13 09:17:13', 0);

-- --------------------------------------------------------

--
-- Structure de la table `failed_password_attempts`
--

CREATE TABLE `failed_password_attempts` (
  `id` char(36) NOT NULL,
  `user_id` char(36) NOT NULL,
  `created` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `games`
--

CREATE TABLE `games` (
  `games_id` int NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `genre` varchar(50) DEFAULT NULL,
  `publisher` varchar(50) DEFAULT NULL,
  `realease_date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `machines`
--

CREATE TABLE `machines` (
  `machine_id` int NOT NULL,
  `processor` varchar(255) DEFAULT NULL,
  `memory` varchar(255) DEFAULT NULL,
  `os` enum('Windows','Mac','Linux') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `type_id` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `machines_games`
--

CREATE TABLE `machines_games` (
  `games_id` int NOT NULL,
  `machine_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `maintenances`
--

CREATE TABLE `maintenances` (
  `id_maintenance` int NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `modified` varchar(50) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `machine_id` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `packages`
--

CREATE TABLE `packages` (
  `id` int NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `price` decimal(15,2) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `duration` time NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `packages`
--

INSERT INTO `packages` (`id`, `name`, `price`, `description`, `duration`) VALUES
(1, 'test', 30.00, 'le test lul', '01:00:00'),
(2, 'Une heure lul', 300.00, 'C\'est une heure', '02:00:00');

-- --------------------------------------------------------

--
-- Structure de la table `reservations`
--

CREATE TABLE `reservations` (
  `id` int NOT NULL,
  `created` datetime DEFAULT CURRENT_TIMESTAMP,
  `expired` datetime DEFAULT NULL,
  `status` enum('Free','Used','Expired') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT 'Free',
  `type_id` int DEFAULT NULL,
  `package_id` int NOT NULL,
  `user_id` char(36) NOT NULL,
  `time` bigint DEFAULT NULL,
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `reservations`
--

INSERT INTO `reservations` (`id`, `created`, `expired`, `status`, `type_id`, `package_id`, `user_id`, `time`, `code`) VALUES
(10, '2024-11-21 08:07:42', '2024-12-21 08:07:42', 'Free', 1, 2, '4c28f30f-6857-4705-8db0-8282291cc303', 0, ''),
(12, '2024-12-04 07:50:40', '2025-01-04 07:50:40', 'Free', 2, 2, 'a3d2f3d2-b6e7-4dd4-a221-0f24dc17fb7a', 0, 'cb7cf47c'),
(13, '2024-12-04 07:53:33', '2025-01-04 07:53:33', 'Free', 1, 1, 'a3d2f3d2-b6e7-4dd4-a221-0f24dc17fb7a', 0, '360becf4'),
(14, '2024-12-04 07:57:55', '2025-01-04 07:57:55', 'Free', 1, 1, 'a3d2f3d2-b6e7-4dd4-a221-0f24dc17fb7a', 10000, '09fd9a00'),
(15, '2024-12-04 08:02:31', '2025-01-04 08:02:31', 'Free', NULL, 1, 'a3d2f3d2-b6e7-4dd4-a221-0f24dc17fb7a', 10000, 'aee3cf32'),
(19, '2025-01-15 11:43:40', NULL, 'Free', 2, 1, '4c28f30f-6857-4705-8db0-8282291cc303', 3600, '095467a3'),
(20, '2025-01-15 11:44:11', NULL, 'Free', 1, 2, 'a3d2f3d2-b6e7-4dd4-a221-0f24dc17fb7a', 7200, 'e9f0cc6b');

--
-- Déclencheurs `reservations`
--
DELIMITER $$
CREATE TRIGGER `before_insert_reservation` BEFORE INSERT ON `reservations` FOR EACH ROW BEGIN
    DECLARE package_duration_seconds BIGINT;

    -- Récupérer la durée du package (en format TIME) et la convertir en secondes
    SELECT TIME_TO_SEC(duration) INTO package_duration_seconds
    FROM packages
    WHERE id = NEW.package_id;

    -- Assigner la durée en secondes à la nouvelle réservation
    SET NEW.time = package_duration_seconds;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `generate_code` BEFORE INSERT ON `reservations` FOR EACH ROW BEGIN
    SET NEW.code = SUBSTRING(MD5(RAND()), 1, 8);
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `update_reservation_time` BEFORE INSERT ON `reservations` FOR EACH ROW BEGIN
    DECLARE package_duration TIME;
    
    -- Récupérer la valeur de `duration` depuis la table `packages`
    SELECT duration INTO package_duration
    FROM packages
    WHERE id = NEW.package_id;

    -- Mettre à jour `time` dans la réservation
    SET NEW.time = package_duration;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Structure de la table `social_accounts`
--

CREATE TABLE `social_accounts` (
  `id` char(36) NOT NULL,
  `user_id` char(36) NOT NULL,
  `provider` varchar(255) NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  `reference` varchar(255) NOT NULL,
  `avatar` text,
  `description` text,
  `link` varchar(255) NOT NULL,
  `token` varchar(500) NOT NULL,
  `token_secret` varchar(500) DEFAULT NULL,
  `token_expires` datetime DEFAULT NULL,
  `active` tinyint(1) NOT NULL DEFAULT '1',
  `data` text NOT NULL,
  `created` datetime NOT NULL,
  `modified` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `types`
--

CREATE TABLE `types` (
  `type_id` int NOT NULL,
  `name` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `types`
--

INSERT INTO `types` (`type_id`, `name`) VALUES
(1, 'fps'),
(2, 'Burautique ');

-- --------------------------------------------------------

--
-- Structure de la table `users`
--

CREATE TABLE `users` (
  `id` char(36) NOT NULL,
  `username` varchar(255) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `token_expires` datetime DEFAULT NULL,
  `api_token` varchar(255) DEFAULT NULL,
  `activation_date` datetime DEFAULT NULL,
  `secret` varchar(32) DEFAULT NULL,
  `secret_verified` tinyint(1) DEFAULT NULL,
  `tos_date` datetime DEFAULT NULL,
  `active` tinyint(1) NOT NULL DEFAULT '1',
  `is_superuser` tinyint(1) NOT NULL DEFAULT '0',
  `role` varchar(255) DEFAULT 'user',
  `created` datetime NOT NULL,
  `modified` datetime NOT NULL,
  `additional_data` text,
  `last_login` datetime DEFAULT NULL,
  `lockout_time` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `users`
--

INSERT INTO `users` (`id`, `username`, `email`, `password`, `first_name`, `last_name`, `token`, `token_expires`, `api_token`, `activation_date`, `secret`, `secret_verified`, `tos_date`, `active`, `is_superuser`, `role`, `created`, `modified`, `additional_data`, `last_login`, `lockout_time`) VALUES
('0ed0283d-240a-4049-a27f-eff66b53b57d', 'Celeste', 'test@ui.com', '$2y$10$zRxFxKZq8Jr3PUyc6rvSf.1Dr/CMlglLMQ0IUWrMgX5CZp/XaUDte', 'Andrea', 'Pastourel', '5a5b75949b7b41b4d9010f9bed574519', '2024-11-13 10:38:30', NULL, NULL, NULL, NULL, '2024-11-13 09:38:30', 0, 0, 'user', '2024-11-13 09:38:30', '2024-11-13 09:38:30', NULL, NULL, NULL),
('2b2568c0-274f-4076-a85b-c5d3d5e628bd', 'jt', 'test@ui.com', '$2y$10$vd/RTBtFhvkG/d/D8fcw3u1mGTuNXa5wtnQbB2XrJPr4HU0bMteLS', 'jerem', 'tar', '38aefb9bb2f115651b82c6397f82fb3a', '2024-11-20 08:27:29', NULL, NULL, NULL, NULL, '2024-11-20 07:27:29', 1, 0, 'user', '2024-11-20 07:27:29', '2024-11-20 07:27:29', NULL, '2024-11-20 07:29:49', NULL),
('3a844bb2-2e09-4728-8448-454f84486412', 'test', 'test@gmail.com', '$2y$10$PuFvBnsjeqJ5YCPQYYb35.d092xultY8X8RGi/YOSlQ7Iytd3jfrW', 'test', 'test', '2687dd27fdfac69b559fa3e004549c10', '2024-11-18 10:07:51', NULL, NULL, NULL, NULL, '2024-11-18 09:07:51', 1, 0, 'user', '2024-11-18 09:07:51', '2024-11-18 09:07:51', NULL, '2024-11-20 08:10:29', NULL),
('4c28f30f-6857-4705-8db0-8282291cc303', 'incorrect', 'inccorect@outlook.com', '$2y$10$nL7HUpnPvBFmMIxH/MTFFOrxEVroZk59qw0pLa8OQlHUvkoYfh7F.', 'jerem', 'tar', '3db178c17a35747303401ff8f80fabd9', '2024-11-21 09:00:49', NULL, NULL, NULL, NULL, '2024-11-21 08:00:49', 1, 0, 'user', '2024-11-21 08:00:49', '2024-11-21 08:00:49', NULL, '2024-11-21 08:01:40', NULL),
('a3d2f3d2-b6e7-4dd4-a221-0f24dc17fb7a', 'superadmin0', 'superadmin@example.com', '$2y$10$C98m.o0gWVwpof2viX/6L.rQAYDuF3Pip./22hplwLtkcHiEhcywO', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, 1, 'superuser', '2024-11-13 09:41:13', '2024-12-11 10:39:18', NULL, '2024-12-04 07:40:20', NULL),
('d3e992d5-2ef7-4f05-b29c-3ab5fc4273f5', 'superadmin', 'superadmin@example.com', '$2y$10$JRpkImSZ/fC.xMBWzk7a/OH.D7IGyKKIJk1O0fkHgH52zxGgrvFW2', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, 1, 'superuser', '2024-11-13 09:19:53', '2024-11-13 09:19:53', NULL, '2024-11-13 09:20:16', NULL);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `cake_d_c_users_phinxlog`
--
ALTER TABLE `cake_d_c_users_phinxlog`
  ADD PRIMARY KEY (`version`);

--
-- Index pour la table `failed_password_attempts`
--
ALTER TABLE `failed_password_attempts`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);

--
-- Index pour la table `games`
--
ALTER TABLE `games`
  ADD PRIMARY KEY (`games_id`);

--
-- Index pour la table `machines`
--
ALTER TABLE `machines`
  ADD PRIMARY KEY (`machine_id`),
  ADD KEY `type_id` (`type_id`);

--
-- Index pour la table `machines_games`
--
ALTER TABLE `machines_games`
  ADD PRIMARY KEY (`games_id`,`machine_id`),
  ADD KEY `machine_id` (`machine_id`);

--
-- Index pour la table `maintenances`
--
ALTER TABLE `maintenances`
  ADD PRIMARY KEY (`id_maintenance`),
  ADD KEY `machine_id` (`machine_id`);

--
-- Index pour la table `packages`
--
ALTER TABLE `packages`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `reservations`
--
ALTER TABLE `reservations`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `package_id` (`package_id`),
  ADD KEY `type_id` (`type_id`);

--
-- Index pour la table `social_accounts`
--
ALTER TABLE `social_accounts`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);

--
-- Index pour la table `types`
--
ALTER TABLE `types`
  ADD PRIMARY KEY (`type_id`);

--
-- Index pour la table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `packages`
--
ALTER TABLE `packages`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `reservations`
--
ALTER TABLE `reservations`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT pour la table `types`
--
ALTER TABLE `types`
  MODIFY `type_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `failed_password_attempts`
--
ALTER TABLE `failed_password_attempts`
  ADD CONSTRAINT `failed_password_attempts_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `machines`
--
ALTER TABLE `machines`
  ADD CONSTRAINT `machines_ibfk_1` FOREIGN KEY (`type_id`) REFERENCES `types` (`type_id`);

--
-- Contraintes pour la table `machines_games`
--
ALTER TABLE `machines_games`
  ADD CONSTRAINT `machines_games_ibfk_1` FOREIGN KEY (`games_id`) REFERENCES `games` (`games_id`),
  ADD CONSTRAINT `machines_games_ibfk_2` FOREIGN KEY (`machine_id`) REFERENCES `machines` (`machine_id`);

--
-- Contraintes pour la table `maintenances`
--
ALTER TABLE `maintenances`
  ADD CONSTRAINT `maintenances_ibfk_1` FOREIGN KEY (`machine_id`) REFERENCES `machines` (`machine_id`);

--
-- Contraintes pour la table `reservations`
--
ALTER TABLE `reservations`
  ADD CONSTRAINT `reservations_ibfk_3` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `reservations_ibfk_4` FOREIGN KEY (`package_id`) REFERENCES `packages` (`id`),
  ADD CONSTRAINT `reservations_ibfk_5` FOREIGN KEY (`type_id`) REFERENCES `types` (`type_id`);

--
-- Contraintes pour la table `social_accounts`
--
ALTER TABLE `social_accounts`
  ADD CONSTRAINT `social_accounts_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
